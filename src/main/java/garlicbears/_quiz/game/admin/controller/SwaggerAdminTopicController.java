package garlicbears._quiz.game.admin.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import garlicbears._quiz.common.entity.ResponseDto;
import garlicbears._quiz.game.admin.dto.ResponseTopicListDto;
import garlicbears._quiz.management.common.dto.ResponseUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

public interface SwaggerAdminTopicController {

	@Operation(summary = "주제 목록 조회", description = "정렬 기준에 따라 주제 목록을 반환합니다.")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved", content = {
		@Content(schema = @Schema(implementation = ResponseTopicListDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)")})
	public ResponseEntity<?> listTopics(
		@RequestParam(defaultValue = "createdAtDesc") @Parameter(schema = @Schema(allowableValues = {"titleDesc",
			"titleAsc", "createdAtDesc", "createdAtAsc", "updatedAtDesc", "updatedAtAsc", "usageCountDesc",
			"usageCountAsc"})) String sort, @RequestParam(defaultValue = "0") int pageNumber,
		@RequestParam(defaultValue = "10") int pageSize);

	@Operation(summary = "엑셀 파일로 주제 생성", description = "엑셀 파일을 통해 주제를 생성합니다. 파일명 = 주제명, A열에 작성된 문제들을 등록합니다.")
	public ResponseEntity<?> createTopicsByExcel(
		@Parameter(description = "multipart/form-data 형식의 엑셀 파일을 받습니다.",
			content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
		@RequestPart("multipartFile") MultipartFile file);

	@Operation(summary = "주제 생성", description = "입력된 주제를 생성합니다.",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schemaProperties = {
			@SchemaProperty(name = "title", schema = @Schema(type = "string", format = "json"))})))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved", content = {
		@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "400", description = "Invalid Input", content = {
			@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)", content = {
			@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "409", description = "Topic Already Exist", content = {
			@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
			@Content(schema = @Schema(implementation = ResponseDto.class))}),})
	public ResponseEntity<?> createTopic(@Valid @RequestBody Map<String, String> request);

	@Operation(summary = "주제 수정", description = "입력된 주제를 수정합니다.",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schemaProperties = {
			@SchemaProperty(name = "title", schema = @Schema(type = "string", format = "json"))})))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved", content = {
		@Content(schema = @Schema(implementation = ResponseUserDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)", content = {
			@Content(schema = @Schema(implementation = ResponseUserDto.class))}),
		@ApiResponse(responseCode = "404", description = "Topic Not Found", content = {
			@Content(schema = @Schema(implementation = ResponseUserDto.class))})})
	public ResponseEntity<?> updateTopic(@PathVariable Long topicId, @Valid @RequestBody Map<String, String> request);

	@Operation(summary = "주제 삭제", description = "입력된 주제를 삭제합니다.")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved", content = {
		@Content(schema = @Schema(implementation = ResponseUserDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)", content = {
			@Content(schema = @Schema(implementation = ResponseUserDto.class))}),
		@ApiResponse(responseCode = "404", description = "Topic Not Found", content = {
			@Content(schema = @Schema(implementation = ResponseUserDto.class))})})
	public ResponseEntity<?> deleteTopic(@PathVariable Long topicId);
}
