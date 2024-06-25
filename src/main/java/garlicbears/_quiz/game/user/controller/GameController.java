package garlicbears._quiz.game.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import garlicbears._quiz.common.config.auth.PrincipalDetails;
import garlicbears._quiz.common.entity.User;
import garlicbears._quiz.game.user.dto.ResponseTopicBadgeDto;
import garlicbears._quiz.game.user.dto.TopicsListDto;
import garlicbears._quiz.game.user.service.GameService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/game")
@Tag(name = "게임")
public class GameController implements SwaggerGameController {

	private final GameService gameService;

	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	/**
	 * 게임 주제 목록 ( 뱃지 미획득 )
	 */
	@GetMapping("/topics")
	public ResponseEntity<?> topicList(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		User user = principalDetails.getUser();
		List<TopicsListDto> topics = gameService.topicList(user.getUserId());

		return ResponseEntity.ok(new ResponseTopicBadgeDto(topics));
	}

	/**
	 * 뱃지 주제 목록
	 */
	@GetMapping("/badges")
	public ResponseEntity<?> badges(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		User user = principalDetails.getUser();
		List<TopicsListDto> topics = gameService.badgeList(user.getUserId());

		return ResponseEntity.ok(new ResponseTopicBadgeDto(topics));
	}

	/**
	 * 게임 주제 선택
	 */
	@GetMapping("/start/{topicId}")
	public ResponseEntity<?> gameStart(@PathVariable(value = "topicId") long topicId,
		@AuthenticationPrincipal PrincipalDetails principalDetails) {
		User user = principalDetails.getUser();

		return ResponseEntity.ok(gameService.gameStart(topicId, user));
	}
}
