package garlicbears._quiz.game.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

import garlicbears._quiz.common.config.auth.PrincipalDetails;
import garlicbears._quiz.common.entity.ResponseDto;
import garlicbears._quiz.game.user.dto.ResponseGameStartDto;
import garlicbears._quiz.game.user.dto.ResponseTopicBadgeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface SwaggerGameController {

	@Operation(summary = "주제 조회", description = "게임 시작 전 뱃지 미획득 주제 조회.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved",
			content = {@Content(schema = @Schema(implementation = ResponseTopicBadgeDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
	})
	public ResponseEntity<?> topicList(@AuthenticationPrincipal PrincipalDetails principalDetails);

	@Operation(summary = "뱃지 조회", description = "획득한 뱃지 조회.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved",
			content = {@Content(schema = @Schema(implementation = ResponseTopicBadgeDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))})
	})
	public ResponseEntity<?> badges(@AuthenticationPrincipal PrincipalDetails principalDetails);

	@Operation(summary = "게임 시작", description = "주제를 선택한 후 게임 시작.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved",
			content = {@Content(schema = @Schema(implementation = ResponseGameStartDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "404", description = "UNKNOWN_TOPIC",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))})
	})
	public ResponseEntity<?> gameStart(@PathVariable(value = "topicId") long topicId,
		@AuthenticationPrincipal PrincipalDetails principalDetails);
}
