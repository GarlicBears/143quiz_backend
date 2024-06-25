package garlicbears._quiz.game.admin.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import garlicbears._quiz.common.entity.ResponseDto;
import garlicbears._quiz.game.admin.dto.ResponseTopicListDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

public interface SwaggerAdminQuestionController {
	@Operation(summary = "문제 목록 조회", description = "정렬 기준에 따라 문제 목록을 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved",
			content = {@Content(schema = @Schema(implementation = ResponseTopicListDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)")
	})
	public ResponseEntity<?> listQuestions(
		@RequestParam(defaultValue = "createdAtDesc") @Parameter(schema = @Schema(allowableValues = {"questionTextDesc",
			"questionTextAsc", "createdAtDesc", "createdAtAsc", "updatedAtDesc", "updatedAtAsc"})) String sort,
		@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize);

	@Operation(summary = "주제별 문제 목록 조회", description = "주제별 문제 목록을 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved",
			content = {@Content(schema = @Schema(implementation = ResponseTopicListDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)"),
		@ApiResponse(responseCode = "404", description = "Topic Not Found")
	})
	public ResponseEntity<?> listQuestionsByTopic(@PathVariable(value = "topicId") long topicId,
		@RequestParam(defaultValue = "createdAtDesc") @Parameter(schema =
		@Schema(allowableValues = {"questionTextDesc", "questionTextAsc", "createdAtDesc", "createdAtAsc",
			"updatedAtDesc", "updatedAtAsc"})) String sort, @RequestParam(defaultValue = "0") int pageNumber,
		@RequestParam(defaultValue = "10") int pageSize);

	@Operation(summary = "문제 생성", description = "입력된 문제를 생성합니다.",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = @Content(
				schemaProperties = {
					@SchemaProperty(name = "title", schema = @Schema(type = "string", format = "json"))
				}
			)
		))
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "400", description = "Invalid Input",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "404", description = "Topic Not Found",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "409", description = "Question Already Exist",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "410", description = "Deleted Topic",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
	})
	public ResponseEntity<?> createQuestion(@PathVariable(value = "topicId") long topicId,
		@Valid @RequestBody Map<String, String> request);

	@Operation(summary = "문제 수정", description = "입력된 문제를 수정합니다.",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = @Content(
				schemaProperties = {
					@SchemaProperty(name = "title", schema = @Schema(type = "string", format = "json"))
				}
			)
		))
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "404", description = "Question Not Found",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))})
	})
	public ResponseEntity<?> updateQuestion(@PathVariable(value = "questionId") long questionId,
		@Valid @RequestBody Map<String, String> request);

	@Operation(summary = "문제 삭제", description = "입력된 문제를 삭제합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "404", description = "Question Not Found",
			content = {@Content(schema = @Schema(implementation = ResponseDto.class))})
	})
	public ResponseEntity<?> deleteQuestion(@PathVariable(value = "questionId") long questionId);
}
