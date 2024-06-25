package garlicbears._quiz.game.admin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import garlicbears._quiz.common.entity.ResponseDto;
import garlicbears._quiz.common.exception.CustomException;
import garlicbears._quiz.common.exception.ErrorCode;
import garlicbears._quiz.game.admin.service.TopicService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
@Tag(name = "주제 관리")
public class AdminTopicController implements SwaggerAdminTopicController {
	private final TopicService topicService;

	@Autowired
	public AdminTopicController(TopicService topicService) {
		this.topicService = topicService;
	}

	@GetMapping("/topics")
	public ResponseEntity<?> listTopics(
		@RequestParam(defaultValue = "createdAtDesc") @Parameter(schema = @Schema(allowableValues = {"titleDesc",
			"titleAsc", "createdAtDesc", "createdAtAsc", "updatedAtDesc", "updatedAtAsc", "usageCountDesc",
			"usageCountAsc"})) String sort, @RequestParam(defaultValue = "0") int pageNumber,
		@RequestParam(defaultValue = "10") int pageSize) {
		// 정렬 조건에 따른 전체 주제 목록 조회
		return ResponseEntity.ok(topicService.getTopicList(pageNumber, pageSize, sort));
	}

	@PostMapping(value = "/topic/upload-excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createTopicsByExcel(
		@Parameter(description = "multipart/form-data 형식의 엑셀 파일을 받습니다.",
			content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
		@RequestPart("multipartFile") MultipartFile file) {
		// 엑셀 파일을 통해 주제 생성
		// 파일명 = 주제명, 첫 번째 시트만 사용, 첫 번째 열 = 주제명
		return ResponseEntity.ok(topicService.saveTopicWithExcel(file));
	}

	@PostMapping("/topic")
	public ResponseEntity<?> createTopic(@Valid @RequestBody Map<String, String> request) {
		// 주제 생성
		String topicTitle = request.get("title");

		if (topicTitle == null || topicTitle.trim().isEmpty()) {
			throw new CustomException(ErrorCode.INVALID_INPUT);
		}

		topicService.save(topicTitle);

		return ResponseEntity.ok(ResponseDto.success());
	}

	@PatchMapping("/topic/{topicId}")
	public ResponseEntity<?> updateTopic(@PathVariable Long topicId, @Valid @RequestBody Map<String, String> request) {
		// 주제 수정
		String topicTitle = request.get("title");

		if (topicTitle == null || topicTitle.trim().isEmpty()) {
			throw new CustomException(ErrorCode.INVALID_INPUT);
		}

		topicService.update(topicId, topicTitle);

		return ResponseEntity.ok(ResponseDto.success());
	}

	@DeleteMapping("/topic/{topicId}")
	public ResponseEntity<?> deleteTopic(@PathVariable Long topicId) {
		// 주제 삭제
		topicService.delete(topicId);

		return ResponseEntity.ok(ResponseDto.success());
	}

}
