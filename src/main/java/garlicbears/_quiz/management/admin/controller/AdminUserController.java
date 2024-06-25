package garlicbears._quiz.management.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import garlicbears._quiz.management.admin.service.AdminUserService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/admin/user")
@Tag(name = "유저 관리")
public class AdminUserController implements SwaggerAdminUserController {
	private final AdminUserService adminUserService;

	public AdminUserController(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	// 유저 목록 조회
	@GetMapping("/")
	public ResponseEntity<?> listUsers(
		@RequestParam(defaultValue = "createdAtDesc") String sort,
		@RequestParam(defaultValue = "0") int pageNumber,
		@RequestParam(defaultValue = "10") int pageSize
	) {
		return ResponseEntity.ok(adminUserService.getUserList(pageNumber, pageSize, sort));
	}

	// 유저 삭제
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(
		@RequestParam(value = "userId") long userId
	) {
		adminUserService.delete(userId);
		return ResponseEntity.ok().build();
	}
}
