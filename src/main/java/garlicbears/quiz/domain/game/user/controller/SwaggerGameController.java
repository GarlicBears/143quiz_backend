package garlicbears.quiz.domain.game.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import garlicbears.quiz.domain.common.dto.ResponseDto;
import garlicbears.quiz.domain.game.user.dto.ResponseGameStartDto;
import garlicbears.quiz.domain.game.user.dto.ResponseTopicBadgeDto;
import garlicbears.quiz.domain.game.user.dto.UserRankingDto;
import garlicbears.quiz.global.config.auth.PrincipalDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

public interface SwaggerGameController {
	@Operation(summary = "게임 랭킹 조회", description = "전체 뱃지 수, 하트 수를 기준으로 랭킹 정보 조회")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved", content = {
		@Content(schema = @Schema(implementation = UserRankingDto.class))}),})
	public ResponseEntity<?> getRankings(@AuthenticationPrincipal PrincipalDetails principalDetails,
		HttpServletRequest request,
		@RequestParam(defaultValue = "0") int pageNumber,
		@RequestParam(defaultValue = "10") int pageSize);

	@Operation(summary = "주제별 게임 랭킹 조회", description = "주제별 하트 수를 기준으로 랭킹 정보 조회")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved", content = {
		@Content(schema = @Schema(implementation = UserRankingDto.class))}),})
	public ResponseEntity<?> getRankingsByTopicId(@AuthenticationPrincipal PrincipalDetails principalDetails,
		HttpServletRequest request,
		@PathVariable(value = "topicId") long topicId,
		@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize);

	@Operation(summary = "주제 조회", description = "게임 시작 전 뱃지 미획득 주제 조회.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved",
			content = {@Content(schema = @Schema(implementation = ResponseTopicBadgeDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
	})
	public ResponseEntity<?> topicList(@AuthenticationPrincipal PrincipalDetails principalDetails,
		HttpServletRequest request);

	@Operation(summary = "뱃지 조회", description = "획득한 뱃지 조회.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved",
			content = {@Content(schema = @Schema(implementation = ResponseTopicBadgeDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))})
	})
	public ResponseEntity<?> badges(@AuthenticationPrincipal PrincipalDetails principalDetails,
		HttpServletRequest request);

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
