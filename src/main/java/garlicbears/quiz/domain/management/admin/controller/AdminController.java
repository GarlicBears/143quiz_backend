package garlicbears.quiz.domain.management.admin.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import garlicbears.quiz.domain.common.dto.ResponseDto;
import garlicbears.quiz.domain.common.entity.Admin;
import garlicbears.quiz.domain.common.entity.Role;
import garlicbears.quiz.domain.management.admin.service.AdminService;
import garlicbears.quiz.domain.management.common.dto.LoginDto;
import garlicbears.quiz.global.exception.CustomException;
import garlicbears.quiz.global.exception.ErrorCode;
import garlicbears.quiz.global.jwt.dto.RefreshTokenDto;
import garlicbears.quiz.global.jwt.service.RefreshTokenService;
import garlicbears.quiz.global.jwt.util.JwtTokenizer;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {
	private static final Logger logger = Logger.getLogger(AdminController.class.getName());
	private final AdminService adminService;
	private final JwtTokenizer jwtTokenizer;
	private final PasswordEncoder passwordEncoder;
	private final RefreshTokenService refreshTokenService;

	public AdminController(AdminService adminService,
		JwtTokenizer jwtTokenizer,
		PasswordEncoder passwordEncoder,
		RefreshTokenService refreshTokenService) {
		this.adminService = adminService;
		this.jwtTokenizer = jwtTokenizer;
		this.passwordEncoder = passwordEncoder;
		this.refreshTokenService = refreshTokenService;
	}

	/**
	 * 관리자 로그인
	 * 유효한 관리자 정보를 전달받아 액세스 토큰과 리프레시 토큰을 발급.
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			throw new CustomException(ErrorCode.INVALID_INPUT);
		}

		Admin admin = adminService.findByEmail(loginDto.getEmail());
		if (!passwordEncoder.matches(loginDto.getPassword(), admin.getPassword())) {
			throw new CustomException(ErrorCode.INVALID_INPUT);
		}

		// 관리자 권한 확인
		List<String> roles = admin.getRoles().stream()
			.map(Role::getRoleName)
			.toList();

		// JWT 토큰을 생성
		String accessToken = jwtTokenizer.createAccessToken(admin.getAdminEmail(), admin.getAdminId(), roles);
		String refreshToken = jwtTokenizer.createAccessToken(admin.getAdminEmail(), admin.getAdminId(), roles);

		// 리프레시 토큰을 Redis에 저장
		refreshTokenService.save(admin.getAdminEmail(), refreshToken);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Token", accessToken);
		headers.set("Refresh-Token", refreshToken);

		return ResponseEntity.ok().headers(headers).body(ResponseDto.success());
	}

	/**
	 * 리프레시 토큰을 이용해 새로운 액세스 토큰을 발급.
	 */
	@PostMapping("/refreshToken")
	public ResponseEntity<?> requestRefresh(@RequestBody RefreshTokenDto refreshTokenDto) {
		// 리프레시 토큰 유효성 확인
		Claims claims = jwtTokenizer.parseRefreshToken(refreshTokenDto.getRefreshToken());
		String email = claims.getSubject();

		// 관리자 이메일로 리프레시 토큰이 있는지 확인
		refreshTokenService.findRefreshToken(email)
			.orElseThrow(() -> new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

		long adminId = Long.valueOf((Integer) claims.get("id"));
		List roles = (List) claims.get("roles");

		// AccessToken 발급
		String accessToken = jwtTokenizer.createAccessToken(email, adminId, roles);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Token", accessToken);
		headers.set("Refresh-Token", refreshTokenDto.getRefreshToken());

		return ResponseEntity.ok().headers(headers).body(ResponseDto.success());
	}

	/**
	 * 로그아웃
	 * 전달받은 리프레시 토큰 삭제
	 */
	@DeleteMapping("/logout")
	public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
		Claims claims = jwtTokenizer.parseRefreshToken(refreshTokenDto.getRefreshToken());
		String email = claims.getSubject();
		// 리프레시 토큰 삭제
		refreshTokenService.deleteRefreshToken(email);
		return ResponseEntity.ok().build();
	}
}
