package garlicbears._quiz.game.admin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import garlicbears._quiz.common.entity.Active;
import garlicbears._quiz.common.entity.ResponseDto;
import garlicbears._quiz.common.entity.Topic;
import garlicbears._quiz.common.exception.CustomException;
import garlicbears._quiz.common.exception.ErrorCode;
import garlicbears._quiz.game.admin.service.QuestionService;
import garlicbears._quiz.game.admin.service.TopicService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
@Tag(name = "문제 관리")
public class AdminQuestionController implements SwaggerAdminQuestionController {
	private final TopicService topicService;
	private final QuestionService questionService;

	@Autowired
	public AdminQuestionController(TopicService topicService, QuestionService questionService) {
		this.topicService = topicService;
		this.questionService = questionService;
	}

	@GetMapping("/questions")
	public ResponseEntity<?> listQuestions(
		@RequestParam(defaultValue = "createdAtDesc") @Parameter(schema = @Schema(allowableValues = {"questionTextDesc",
			"questionTextAsc", "createdAtDesc", "createdAtAsc", "updatedAtDesc",
			"updatedAtAsc"})) String sort, @RequestParam(defaultValue = "0") int pageNumber,
		@RequestParam(defaultValue = "10") int pageSize) {
		// 정렬 기준에 따른 전체 문제 목록 조회
		return ResponseEntity.ok(questionService.getQuestionList(pageNumber, pageSize, sort));
	}

	@GetMapping("/topic/{topicId}/questions")
	public ResponseEntity<?> listQuestionsByTopic(@PathVariable(value = "topicId") long topicId,
		@RequestParam(defaultValue = "createdAtDesc") @Parameter(schema =
		@Schema(allowableValues = {"questionTextDesc", "questionTextAsc", "createdAtDesc", "createdAtAsc",
			"updatedAtDesc", "updatedAtAsc"})) String sort, @RequestParam(defaultValue = "0") int pageNumber,
		@RequestParam(defaultValue = "10") int pageSize) {
		// 주제별 문제 목록 조회
		Topic topic = topicService.findByTopicId(topicId)
			.orElseThrow(() -> new CustomException(ErrorCode.TOPIC_NOT_FOUND));

		return ResponseEntity.ok(questionService.getQuestionListByTopic(topic, pageNumber, pageSize, sort));
	}

	@PostMapping("/topic/{topicId}/question")
	public ResponseEntity<?> createQuestion(@PathVariable(value = "topicId") long topicId,
		@Valid @RequestBody Map<String, String> request) {
		// 문제 생성
		String questionAnswerText = request.get("title");

		if (questionAnswerText == null || questionAnswerText.trim().isEmpty()) {
			throw new CustomException(ErrorCode.INVALID_INPUT);
		}

		Topic topic = topicService.findByTopicId(topicId)
			.orElseThrow(() -> new CustomException(ErrorCode.TOPIC_NOT_FOUND));

		if (topic.getTopicActive() == Active.inactive) {
			throw new CustomException(ErrorCode.DELETED_TOPIC);
		}

		questionService.save(topic, questionAnswerText);

		return ResponseEntity.ok(ResponseDto.success());
	}

	@PatchMapping("/question/{questionId}")
	public ResponseEntity<?> updateQuestion(@PathVariable(value = "questionId") long questionId,
		@Valid @RequestBody Map<String, String> request) {
		// 문제 수정
		String questionText = request.get("title");

		if (questionText == null || questionText.trim().isEmpty()) {
			throw new CustomException(ErrorCode.INVALID_INPUT);
		}

		questionService.update(questionId, questionText);

		return ResponseEntity.ok(ResponseDto.success());
	}

	@DeleteMapping("/question/{questionId}")
	public ResponseEntity<?> deleteQuestion(@PathVariable(value = "questionId") long questionId) {
		// 문제 삭제
		questionService.delete(questionId);

		return ResponseEntity.ok(ResponseDto.success());
	}
}
