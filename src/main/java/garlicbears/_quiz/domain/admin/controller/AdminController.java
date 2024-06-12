package garlicbears._quiz.domain.admin.controller;

import garlicbears._quiz.domain.game.entity.Topic;
import garlicbears._quiz.domain.game.service.QuestionService;
import garlicbears._quiz.domain.game.service.TopicService;
import garlicbears._quiz.global.dto.ResponseDto;
import garlicbears._quiz.global.entity.Active;
import garlicbears._quiz.global.exception.CustomException;
import garlicbears._quiz.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final TopicService topicService;
    private final QuestionService questionService;

    @PostMapping("/topics")
    @Operation(summary = "주제 생성", description = "입력된 주제를 생성합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schemaProperties = {
                                    @SchemaProperty(name = "title", schema = @Schema(type="string", format = "json"))
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
            @ApiResponse(responseCode = "409", description = "Nickname Already Exist",
                    content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
    })
    public ResponseEntity<?> createTopic(@Valid @RequestBody Map<String, String> request) {
        String topicTitle = request.get("title");

        if (topicTitle == null || topicTitle.trim().isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

        topicService.save(topicTitle);

        return ResponseEntity.ok(ResponseDto.success());
    }

    @PostMapping("/topics/{topicId}/questions")
    @Operation(summary = "문제 생성", description = "입력된 문제를 생성합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schemaProperties = {
                                    @SchemaProperty(name = "title", schema = @Schema(type="string", format = "json"))
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
            @ApiResponse(responseCode = "404", description = "Unknown Topic",
                    content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
            @ApiResponse(responseCode = "409", description = "Nickname Already Exist",
                    content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
            @ApiResponse(responseCode = "410", description = "Deleted Topic",
                    content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(schema = @Schema(implementation = ResponseDto.class))}),
    })
    public ResponseEntity<?> createQuestion(@PathVariable(value="topicId") long topicId,
                                            @Valid @RequestBody Map<String, String> request) {
        String questionText = request.get("title");

        if (questionText == null || questionText.trim().isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

        Optional<Topic> topic = topicService.findByTopicId(topicId);
        if (topic.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_TOPIC);
        }
        if (topic.get().getTopicActive() == Active.inactive) {
            throw new CustomException(ErrorCode.DELETED_TOPIC);
        }

        questionService.save(topic.get(), questionText);

        return ResponseEntity.ok(ResponseDto.success());
    }
}
