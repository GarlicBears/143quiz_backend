package garlicbears._quiz.game.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import garlicbears._quiz.common.config.auth.PrincipalDetails;
import garlicbears._quiz.common.entity.ResponseDto;
import garlicbears._quiz.game.user.dto.RequestUserAnswerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

public interface SwaggerUserAnswerController {

	@Operation(summary = "사용자 게임 답변", description = "게임 종료 후 사용자 답변 저장")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved",
			content = {@Content(schema = @Schema(implementation = RequestUserAnswerDto.class))}),
		@ApiResponse(responseCode = "400", description = "MISSING_REQUEST_BODY_VARIABLE",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "400", description = "HEARTS_COUNT_EXCEEDSS",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "404", description = "UNKNOWN_TOPIC",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "404", description = "UNKNOWN_GAMESESSION",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "404", description = "UNKNOWN_QUESTION",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))})
	})
	public ResponseEntity<?> userAnswerSave(@Valid @RequestBody RequestUserAnswerDto requestUserAnswerDto,
		BindingResult bindingResult,
		@AuthenticationPrincipal PrincipalDetails principalDetails);

	@Operation(summary = "사용자 게임 중도 이탈", description = "사용자가 게임을 중도 이탈했을 경우")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "404", description = "UNKNOWN_GAMESESSION",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))})
	})
	public ResponseEntity<?> userAnswerDrop(@PathVariable(value = "sessionId") long sessionId,
		@AuthenticationPrincipal PrincipalDetails principalDetails);
}
