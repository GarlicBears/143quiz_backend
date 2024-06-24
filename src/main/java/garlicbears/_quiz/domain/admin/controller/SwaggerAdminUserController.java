package garlicbears._quiz.domain.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import garlicbears._quiz.domain.user.dto.ResponseUserListDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface SwaggerAdminUserController {
	@Operation(summary = "유저 목록 조회", description = "정렬 기준에 따라 유저 목록을 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved",
			content = {@Content(schema = @Schema(implementation = ResponseUserListDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)")
	})
	public ResponseEntity<?> listUsers(
		@RequestParam(defaultValue = "createdAtDesc") String sort,
		@RequestParam(defaultValue = "0") int pageNumber,
		@RequestParam(defaultValue = "10") int pageSize
	);
}
