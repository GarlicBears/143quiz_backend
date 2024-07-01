package garlicbears.quiz.domain.management.user.controller;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import garlicbears.quiz.domain.common.dto.ResponseDto;
import garlicbears.quiz.domain.common.entity.Role;
import garlicbears.quiz.domain.common.entity.User;
import garlicbears.quiz.domain.management.common.dto.LoginDto;
import garlicbears.quiz.domain.management.common.dto.ResponseUserDto;
import garlicbears.quiz.domain.management.user.dto.SignUpDto;
import garlicbears.quiz.domain.management.user.dto.UpdateUserDto;
import garlicbears.quiz.domain.management.user.service.UserRatingService;
import garlicbears.quiz.domain.management.user.service.UserService;
import garlicbears.quiz.global.exception.CustomException;
import garlicbears.quiz.global.exception.ErrorCode;
import garlicbears.quiz.global.jwt.dto.RefreshTokenDto;
import garlicbears.quiz.global.jwt.service.RefreshTokenService;
import garlicbears.quiz.global.jwt.util.JwtTokenizer;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Tag(name = "회원 관리")
public class UserController implements SwaggerUserController {
	private static final Logger logger = Logger.getLogger(UserController.class.getName());
	private final UserService userService;
	private final UserRatingService userRatingService;
	private final JwtTokenizer jwtTokenizer;
	private final PasswordEncoder passwordEncoder;
	private final RefreshTokenService refreshTokenService;

	@Autowired
	UserController(UserService userService,
		UserRatingService userRatingService,
		JwtTokenizer jwtTokenizer,
		PasswordEncoder passwordEncoder,
		RefreshTokenService refreshTokenService) {
		this.userService = userService;
		this.userRatingService = userRatingService;
		this.jwtTokenizer = jwtTokenizer;
		this.passwordEncoder = passwordEncoder;
		this.refreshTokenService = refreshTokenService;
	}

	/**
	 * 회원가입 시 이메일 중복 체크
	 */
	@PostMapping("/checkEmail")
	public ResponseEntity<?> checkEmail(@Valid @RequestBody Map<String, String> request) {
		String email = request.get("email");
		if (email == null || email.trim().isEmpty()) {
			throw new CustomException(ErrorCode.INVALID_INPUT);
		}
		userService.checkDuplicatedEmail(email);
		return ResponseEntity.ok(ResponseDto.success());
	}

	/**
	 * 회원가입 시 닉네임 중복 체크
	 */
	@PostMapping("/checkNickname")
	public ResponseEntity<?> checkNickname(@Valid @RequestBody Map<String, String> request) {
		String nickname = request.get("nickname");
		if (nickname == null || nickname.trim().isEmpty()) {
			throw new CustomException(ErrorCode.INVALID_INPUT);
		}
		userService.checkDuplicatedNickname(nickname);
		return ResponseEntity.ok(ResponseDto.success());
	}

	/**
	 * 회원가입
	 */
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@Valid @RequestBody SignUpDto signUpDto,
		BindingResult bindingResult) {
		// 유효성 검사 에러가 있는 경우
		if (bindingResult.hasErrors()) {
			StringBuilder errorMessage = new StringBuilder();
			bindingResult.getFieldErrors()
				.forEach(error -> errorMessage.append(error.getField())
					.append(": ")
					.append(error.getDefaultMessage())
					.append("."));
			logger.warning("errorMessage : " + errorMessage.toString());
			throw new CustomException(ErrorCode.BAD_REQUEST);
		}
		userService.checkDuplicatedEmail(signUpDto.getEmail());
		userService.checkDuplicatedNickname(signUpDto.getNickname());
		userService.signUp(signUpDto);
		return ResponseEntity.ok(ResponseDto.success());
	}

	@GetMapping("/")
	public ResponseEntity<?> searchUser(@AuthenticationPrincipal UserDetails userDetails) {
		// 현재 인증된 사용자의 정보를 principalDetails로부터 가져올 수 있습니다.
		if (userDetails == null) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		}
		// 현재 인증된 사용자의 정보를 UserDetails로부터 가져올 수 있습니다.
		User user = userService.findByEmail(userDetails.getUsername());

		System.out.println(user);

		return ResponseEntity.ok(ResponseUserDto.fromUser(user));
	}

	@PatchMapping("/")
	public ResponseEntity<?> updateUser(@AuthenticationPrincipal UserDetails userDetails,
		@RequestBody UpdateUserDto updateUserDto) {
		if (userDetails == null) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		}
		// 현재 인증된 사용자의 정보를 UserDetails로부터 가져올 수 있습니다.
		User user = userService.findByEmail(userDetails.getUsername());

		userService.update(user, updateUserDto);

		return ResponseEntity.ok(ResponseUserDto.fromUser(user));
	}

	@DeleteMapping("/")
	public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
		if (userDetails == null) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		}
		// 현재 인증된 사용자의 정보를 UserDetails로부터 가져올 수 있습니다.
		User user = userService.findByEmail(userDetails.getUsername());

		userService.delete(user);

		return ResponseEntity.ok(ResponseDto.success());
	}

	@PostMapping("/rating")
	public ResponseEntity<?> rating(@AuthenticationPrincipal UserDetails userDetails,
		@RequestBody Map<String, Double> request) {
		if (userDetails == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
		}
		// 현재 인증된 사용자의 정보를 UserDetails로부터 가져올 수 있습니다.
		User user = userService.findByEmail(userDetails.getUsername());
		Double ratingValue = request.get("ratingValue");

		userRatingService.saveRating(user, ratingValue);

		return ResponseEntity.ok(ResponseDto.success());
	}

	/**
	 * 로그인
	 * 유효한 사용자 정보를 전달받아 액세스 토큰과 리프레시 토큰을 발급.
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult
		//HttpServletResponse response
		) {

		if (bindingResult.hasErrors()) {
			throw new CustomException(ErrorCode.INVALID_INPUT);
		}

		User user = userService.findByEmail(loginDto.getEmail());
		if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
			throw new CustomException(ErrorCode.INVALID_INPUT);
		}

		// 사용자의 역할(Role)을 문자열 리스트로 변환
		List<String> roles = user.getRoles().stream()
			.map(Role::getRoleName)
			.toList();

		// JWT토큰을 생성
		String accessToken = jwtTokenizer.createAccessToken(user.getUserEmail(), user.getUserId(), roles);
		String refreshToken = jwtTokenizer.createRefreshToken(user.getUserEmail(), user.getUserId(), roles);

		// 리프레시 토큰을 Redis에 저장
		refreshTokenService.save(user.getUserEmail(), refreshToken);

		// // 리프레시 토큰을 쿠키에 저장
		// response.addCookie(createRefreshTokenCookie(refreshToken));

		HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Token", accessToken);

		return ResponseEntity.ok().headers(headers).body(ResponseDto.success());
	}

	// /**
	//  * 리프레시 토큰을 쿠키에 저장
	//  */
	// private Cookie createRefreshTokenCookie(String refreshToken) {
	// 	Cookie refreshTokenCookie = new Cookie("Refresh-Token", refreshToken);
	// 	refreshTokenCookie.setHttpOnly(true); // JavaScript에서 접근 불가능하도록 설정
	// 	refreshTokenCookie.setPath("/");
	// 	refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
	// 	return refreshTokenCookie;
	// }

	/**
	 * 리프레시 토큰을 이용해 새로운 액세스 토큰을 발급.
	 */
	@PostMapping("/refreshToken")
	public ResponseEntity<?> requestRefresh(@RequestBody RefreshTokenDto refreshTokenDto) {
		// 쿠키에서 리프레시 토큰을 읽기
		//String refreshToken = getRefreshTokenFromCookies(request);

		// 전달받은 유저의 아이디로 유저가 존재하는지 확인
		Claims claims = jwtTokenizer.parseRefreshToken(refreshTokenDto.getRefreshToken());
		//Claims claims = jwtTokenizer.parseRefreshToken(refreshToken);
		String email = claims.getSubject();

		// 전달받은 이메일로 리프레시 토큰이 존재하는지 확인하고, 존재하지 않으면 예외를 발생
		refreshTokenService.findRefreshToken(email)
			.orElseThrow(() -> new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

		long userId = Long.valueOf((Integer)claims.get("id"));
		List roles = (List)claims.get("roles");

		// Token을 발급
		String accessToken = jwtTokenizer.createAccessToken(email, userId, roles);
		//String newRefreshToken = jwtTokenizer.createRefreshToken(email, userId, roles);

		// // 기존 리프레시 토큰 삭제 후 새로운 리프레시 토큰 저장
		// refreshTokenService.deleteRefreshToken(email);
		//refreshTokenService.save(email, newRefreshToken);

		// 새로운 리프레시 토큰을 쿠키에 저장
		//response.addCookie(createRefreshTokenCookie(newRefreshToken));

		HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Token", accessToken);
		headers.set("Refresh-Token", refreshTokenDto.getRefreshToken());

		return ResponseEntity.ok().headers(headers).body(ResponseDto.success());
	}

	// /**
	//  * 쿠키에서 리프레시 토큰을 읽음
	//  */
	// private String getRefreshTokenFromCookies(HttpServletRequest request) {
	// 	Cookie[] cookies = request.getCookies();
	// 	if (cookies != null) {
	// 		for (Cookie cookie : cookies) {
	// 			if ("Refresh-Token".equals(cookie.getName())) {
	// 				return cookie.getValue();
	// 			}
	// 		}
	// 	}
	// 	throw new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
	// }

	// /**
	//  * 리프레시 토큰을 쿠키에서 삭제하는 메서드.
	//  */
	// private Cookie deleteRefreshTokenCookie() {
	// 	Cookie refreshTokenCookie = new Cookie("Refresh-Token", null);
	// 	refreshTokenCookie.setHttpOnly(true); // JavaScript에서 접근 불가능하도록 설정
	// 	refreshTokenCookie.setPath("/");
	// 	refreshTokenCookie.setMaxAge(0); // 쿠키 삭제
	// 	return refreshTokenCookie;
	// }

	/**
	 * 로그아웃
	 * 전달받은 리프레시 토큰 삭제
	 */
	@DeleteMapping("/logout")
	public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
		//String refreshToken = getRefreshTokenFromCookies(request);
		Claims claims = jwtTokenizer.parseRefreshToken(refreshTokenDto.getRefreshToken());
		String email = claims.getSubject();
		// 리프레시 토큰 삭제
		refreshTokenService.deleteRefreshToken(email);
		// 쿠키에서 리프레시 토큰 삭제
		//response.addCookie(deleteRefreshTokenCookie());

		return ResponseEntity.ok().build();
	}

}
