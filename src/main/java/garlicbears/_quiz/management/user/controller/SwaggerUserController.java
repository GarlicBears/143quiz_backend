package garlicbears._quiz.management.user.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import garlicbears._quiz.common.config.auth.PrincipalDetails;
import garlicbears._quiz.common.entity.ResponseDto;
import garlicbears._quiz.management.common.dto.ResponseUserDto;
import garlicbears._quiz.management.user.dto.SignUpDto;
import garlicbears._quiz.management.user.dto.UpdateUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

public interface SwaggerUserController {
	@Operation(summary = "email 중복 확인", description = "입력된 이메일이 이미 가입된 유저인지 확인합니다.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schemaProperties = {
		@SchemaProperty(name = "email", schema = @Schema(type = "string", format = "json"))})))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved", content = {
		@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "409", description = "Email Already Exist", content = {
			@Content(schema = @Schema(implementation = ResponseDto.class))}),})
	public ResponseEntity<?> checkEmail(@Valid @RequestBody Map<String, String> request);

	@Operation(summary = "nickname 중복 확인", description = "입력된 닉네임이 이미 가입된 유저인지 확인합니다.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schemaProperties = {
		@SchemaProperty(name = "nickname", schema = @Schema(type = "string", format = "json"))})))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved", content = {
		@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "409", description = "Nickname Already Exist", content = {
			@Content(schema = @Schema(implementation = ResponseDto.class))}),})
	public ResponseEntity<?> checkNickname(@Valid @RequestBody Map<String, String> request);

	@Operation(summary = "회원가입", description = "입력받은 정보를 바탕으로 회원을 생성합니다.")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved", content = {
		@Content(schema = @Schema(implementation = ResponseUserDto.class))}),
		@ApiResponse(responseCode = "400", description = "Bad Request", content = {
			@Content(schema = @Schema(implementation = ResponseUserDto.class))})})
	public ResponseEntity<?> signUp(@Valid @RequestBody SignUpDto signUpDto, BindingResult bindingResult);

	@Operation(summary = "유저 정보(내 정보) 조회", description = "jwt token을 받아 유저 정보를 반환합니다.")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved", content = {
		@Content(schema = @Schema(implementation = ResponseUserDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)")})
	public ResponseEntity<?> searchUser(@AuthenticationPrincipal PrincipalDetails principalDetails);

	@Operation(summary = "유저 정보(내 정보) 수정", description = "jwt token을 받아 유저 정보를 확인. resquest body로 전달받은 정보로 수정합니다.")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved", content = {
		@Content(schema = @Schema(implementation = ResponseUserDto.class))}),
		@ApiResponse(responseCode = "400", description = "Bad Request (Invalid Input)", content = {
			@Content(schema = @Schema(implementation = ResponseUserDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)", content = {
			@Content(schema = @Schema(implementation = ResponseUserDto.class))})})
	public ResponseEntity<?> updateUser(@AuthenticationPrincipal PrincipalDetails principalDetails,
		@RequestBody UpdateUserDto updateUserDto);

	@Operation(summary = "계정 탈퇴", description = "jwt token을 받아 유저 정보를 확인. status 를 inactive로 변환합니다.")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved", content = {
		@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)", content = {
			@Content(schema = @Schema(implementation = ResponseDto.class))})})
	public ResponseEntity<?> deleteUser(@AuthenticationPrincipal PrincipalDetails principalDetails);

	@Operation(summary = "평점 주기", description = "jwt token을 받아 유저 정보를 확인. request body로 평점을 전달받아 저장합니다.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schemaProperties = {
		@SchemaProperty(name = "ratingValue", schema = @Schema(type = "double", format = "json"))})))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved", content = {
		@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "400", description = "Bad Request (Invalid Input)", content = {
			@Content(schema = @Schema(implementation = ResponseDto.class))}),
		@ApiResponse(responseCode = "403", description = "Forbidden (Invalid token)", content = {
			@Content(schema = @Schema(implementation = ResponseDto.class))})})
	public ResponseEntity<?> rating(@AuthenticationPrincipal PrincipalDetails principalDetails,
		@RequestBody Map<String, Double> request);
}
