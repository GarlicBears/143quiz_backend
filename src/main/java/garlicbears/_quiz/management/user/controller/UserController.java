package garlicbears._quiz.management.user.controller;

import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import garlicbears._quiz.common.config.auth.PrincipalDetails;
import garlicbears._quiz.common.entity.ResponseDto;
import garlicbears._quiz.common.entity.User;
import garlicbears._quiz.common.exception.CustomException;
import garlicbears._quiz.common.exception.ErrorCode;
import garlicbears._quiz.management.common.dto.ResponseUserDto;
import garlicbears._quiz.management.user.dto.SignUpDto;
import garlicbears._quiz.management.user.dto.UpdateUserDto;
import garlicbears._quiz.management.user.service.RatingService;
import garlicbears._quiz.management.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Tag(name = "회원 관리")
public class UserController implements SwaggerUserController {
	private static final Logger logger = Logger.getLogger(UserController.class.getName());
	private final UserService userService;
	private final RatingService ratingService;

	@Autowired
	UserController(UserService userService, RatingService ratingService) {
		this.userService = userService;
		this.ratingService = ratingService;
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
	public ResponseEntity<?> searchUser(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		// 현재 인증된 사용자의 정보를 principalDetails로부터 가져올 수 있습니다.
		User user = principalDetails.getUser();

		System.out.println(user);

		return ResponseEntity.ok(ResponseUserDto.fromUser(user));
	}

	@PatchMapping("/")
	public ResponseEntity<?> updateUser(@AuthenticationPrincipal PrincipalDetails principalDetails,
		@RequestBody UpdateUserDto updateUserDto) {
		// 현재 인증된 사용자의 정보를 principalDetails로부터 가져올 수 있습니다.
		User user = principalDetails.getUser();

		userService.update(user, updateUserDto);

		return ResponseEntity.ok(ResponseUserDto.fromUser(user));
	}

	@DeleteMapping("/")
	public ResponseEntity<?> deleteUser(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		User user = principalDetails.getUser();

		userService.delete(user);

		return ResponseEntity.ok(ResponseDto.success());
	}

	@PostMapping("/rating")
	public ResponseEntity<?> rating(@AuthenticationPrincipal PrincipalDetails principalDetails,
		@RequestBody Map<String, Double> request) {
		User user = principalDetails.getUser();
		Double ratingValue = request.get("ratingValue");

		ratingService.saveRating(user, ratingValue);

		return ResponseEntity.ok(ResponseDto.success());
	}
}
