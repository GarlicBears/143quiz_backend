package garlicbears.quiz.domain.game.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import garlicbears.quiz.domain.game.admin.dto.VisitorCountDto;

public interface AdminLogQueryRepository {
	Page<VisitorCountDto> getDailyVisitors(Pageable pageable);
	Page<VisitorCountDto> getWeeklyVisitors(Pageable pageable);
	Page<VisitorCountDto> getMonthlyVisitors(Pageable pageable);
	long getDailyActiveUserCount();
}
