package garlicbears.quiz.domain.game.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import garlicbears.quiz.domain.common.service.LogService;
import garlicbears.quiz.domain.common.entity.User;
import garlicbears.quiz.domain.game.admin.service.TopicService;
import garlicbears.quiz.domain.game.common.service.RankingService;
import garlicbears.quiz.domain.game.user.dto.ResponseTopicBadgeDto;
import garlicbears.quiz.domain.game.user.dto.TopicsListDto;
import garlicbears.quiz.domain.game.user.service.GameService;
import garlicbears.quiz.global.config.auth.PrincipalDetails;
import garlicbears.quiz.global.exception.CustomException;
import garlicbears.quiz.global.exception.ErrorCode;
import garlicbears.quiz.global.handler.ClientIpHandler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/game")
@Tag(name = "게임")
public class GameController implements SwaggerGameController {

	private final GameService gameService;
	private final TopicService topicService;
	private final RankingService rankingService;
	private final LogService logService;

	public GameController(GameService gameService, TopicService topicService,
		LogService logService, RankingService rankingService) {
		this.gameService = gameService;
		this.topicService = topicService;
		this.logService = logService;
		this.rankingService = rankingService;
	}

	@GetMapping("/rankings")
	public ResponseEntity<?> getRankings(@AuthenticationPrincipal PrincipalDetails principalDetails,
		HttpServletRequest request,
		@RequestParam(defaultValue = "0") int pageNumber,
		@RequestParam(defaultValue = "10") int pageSize) {
		User user = principalDetails.getUser();

		logService.log(user, request.getRequestURI(), ClientIpHandler.getClientIp(request));

		return ResponseEntity.ok(rankingService.getRankings(pageNumber, pageSize));
	}

	@GetMapping("/rankings/{topicId}")
	public ResponseEntity<?> getRankingsByTopicId(@AuthenticationPrincipal PrincipalDetails principalDetails,
		HttpServletRequest request,
		@PathVariable(value = "topicId") long topicId,
		@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
		User user = principalDetails.getUser();

		topicService.findByTopicId(topicId).orElseThrow(() -> new CustomException(ErrorCode.TOPIC_NOT_FOUND));

		logService.log(user, request.getRequestURI(), ClientIpHandler.getClientIp(request));

		return ResponseEntity.ok(rankingService.getRankingsByTopicId(topicId, pageNumber, pageSize));
	}

	/**
	 * 게임 주제 목록 ( 뱃지 미획득 )
	 */
	@GetMapping("/topics")
	public ResponseEntity<?> topicList(@AuthenticationPrincipal PrincipalDetails principalDetails,
		HttpServletRequest request) {
		User user = principalDetails.getUser();
		List<TopicsListDto> topics = gameService.topicList(user.getUserId());

		logService.log(user, request.getRequestURI(), ClientIpHandler.getClientIp(request));

		return ResponseEntity.ok(new ResponseTopicBadgeDto(topics));
	}

	/**
	 * 뱃지 주제 목록
	 */
	@GetMapping("/badges")
	public ResponseEntity<?> badges(@AuthenticationPrincipal PrincipalDetails principalDetails,
		HttpServletRequest request) {
		User user = principalDetails.getUser();
		List<TopicsListDto> topics = gameService.badgeList(user.getUserId());

		logService.log(user, request.getRequestURI(), ClientIpHandler.getClientIp(request));

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
