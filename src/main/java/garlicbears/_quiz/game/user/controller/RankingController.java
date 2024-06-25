package garlicbears._quiz.game.user.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import garlicbears._quiz.common.exception.CustomException;
import garlicbears._quiz.common.exception.ErrorCode;
import garlicbears._quiz.game.user.service.RankingService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/game")
@Tag(name = "게임")
public class RankingController implements SwaggerRankingController {

	private final RankingService rankingService;
	public RankingController(RankingService rankingService) {
		this.rankingService = rankingService;
	}

	@GetMapping("/rankings")
	public ResponseEntity<?> getRankings(@RequestParam(defaultValue = "0") int pageNumber,
		@RequestParam(defaultValue = "10") int pageSize) {

		return ResponseEntity.ok(rankingService.getRankings(pageNumber, pageSize));
	}

	@GetMapping("/rankings/{topicId}")
	public ResponseEntity<?> getRankingsByTopicId(@PathVariable(value = "topicId") long topicId,
		@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {

		rankingService.findByTopicId(topicId).orElseThrow(() -> new CustomException(ErrorCode.TOPIC_NOT_FOUND));

		return ResponseEntity.ok(rankingService.getRankingsByTopicId(topicId, pageNumber, pageSize));
	}
}