package garlicbears.quiz.domain.game.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import garlicbears.quiz.domain.game.admin.service.GameStatService;
import garlicbears.quiz.domain.game.common.service.RankingService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/admin/stat")
@Tag(name = "통계 관리")
public class AdminStatController implements SwaggerAdminStatController{
	private final GameStatService gameStatService;
	private final RankingService rankingService;

	public AdminStatController(GameStatService gameStatService,
		RankingService rankingService) {
		this.gameStatService = gameStatService;
		this.rankingService = rankingService;
	}

	/**
	 * 별점 통계 조회
	 */
	@GetMapping("/rating")
	public ResponseEntity<?> getRatingStat() {
		return ResponseEntity.ok(gameStatService.getRatingStat());
	}

	/**
	 * 대시보드 조회
	 * 총 사이트 방문자수, 일일 활성 사용자 수, 일일 게임 플레이 수, 전체 사용자 수, 평균 평점
	 */
	@GetMapping("/dashboard")
	public ResponseEntity<?> getDashboard() {
		return ResponseEntity.ok(gameStatService.getDashboard());
	}

	/**
	 * 유저 랭킹 조회
	 */
	@GetMapping("/userRanking")
	public ResponseEntity<?> getUserRanking(@RequestParam(defaultValue = "0") int pageNumber,
		@RequestParam(defaultValue = "30") int pageSize) {
		return ResponseEntity.ok(rankingService.getRankings(pageNumber, pageSize));
	}

	/**
	 * 게임 통계 리스트 조회
	 */
	@GetMapping("/game")
	public ResponseEntity<?> getGameStatList(@RequestParam(defaultValue = "createdAtDesc") @Parameter(schema = @Schema(allowableValues = {"titleDesc",
		"titleAsc", "createdAtDesc", "createdAtAsc", "updatedAtDesc", "updatedAtAsc", "usageCountDesc",
		"usageCountAsc"})) String sort, @RequestParam(defaultValue = "0") int pageNumber,
		@RequestParam(defaultValue = "10") int pageSize) {
		return ResponseEntity.ok(gameStatService.getGameStatList(pageNumber, pageSize, sort));
	}

	/**
	 * 총 방문자 수 조회
	 * 일일 방문자 수의 총 합으로 처리
	 */
	@GetMapping("/totalVisitors")
	public ResponseEntity<?> getTotalVisitors() {
		return ResponseEntity.ok(gameStatService.getTotalVisitors());
	}

	/**
	 * 일일 방문자 수 조회
	 */
	@GetMapping("/dailyVisitors")
	public ResponseEntity<?> getDailyVisitors(@RequestParam(defaultValue = "0") int pageNumber,
		@RequestParam(defaultValue = "10") int pageSize) {
		return ResponseEntity.ok(gameStatService.getDailyVisitors(pageNumber, pageSize));
	}

	/**
	 * 주간 방문자 수 조회
	 */
	@GetMapping("/weeklyVisitors")
	public ResponseEntity<?> getWeeklyVisitors(@RequestParam(defaultValue = "0") int pageNumber,
		@RequestParam(defaultValue = "10") int pageSize) {
		return ResponseEntity.ok(gameStatService.getWeeklyVisitors(pageNumber, pageSize));
	}

	/**
	 * 월별 방문자 수 조회
	 */
	@GetMapping("/monthlyVisitors")
	public ResponseEntity<?> getMonthlyVisitors(@RequestParam(defaultValue = "0") int pageNumber,
		@RequestParam(defaultValue = "10") int pageSize) {
		return ResponseEntity.ok(gameStatService.getMonthlyVisitors(pageNumber, pageSize));
	}
}
