package garlicbears.quiz.domain.management.admin.controller;

import java.util.Map;
import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import garlicbears.quiz.domain.common.dto.ResponseDto;
import garlicbears.quiz.domain.management.admin.dto.AdminSignUpDto;
import garlicbears.quiz.domain.management.admin.service.AdminService;
import garlicbears.quiz.global.exception.CustomException;
import garlicbears.quiz.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/admin")
@Tag(name = "관리자 회원 관리")
public class AdminController implements SwaggerAdminController {
	private static final Logger logger = Logger.getLogger(AdminController.class.getName());
	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * 관리자 목록 조회
	 */
	@Override
	@GetMapping("/")
	public ResponseEntity<?> listAdmins(
		@RequestParam(defaultValue = "createdAtDesc") String sort,
		@RequestParam(defaultValue = "0") int pageNumber,
		@RequestParam(defaultValue = "10") int pageSize) {
		return ResponseEntity.ok(adminService.getAdminList(pageNumber, pageSize, sort));
	}

	/**
	 * 관리자 권한 변경
	 */
	@Override
	// @PatchMapping("/changeRole/{adminId}")
	public ResponseEntity<?> changeAdminRole(@RequestParam(value = "adminId") long adminId) {
		// TODO JWT 토큰이 완성되면 구현
		return null;
	}

	/**
	 * 관리자 삭제
	 */
	@DeleteMapping("/delete/{adminId}")
	public ResponseEntity<?> deleteAdmin(@PathVariable long adminId) {
		adminService.delete(adminId);
		return ResponseEntity.ok(ResponseDto.success());
	}
}
