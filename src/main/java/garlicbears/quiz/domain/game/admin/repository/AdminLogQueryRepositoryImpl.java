package garlicbears.quiz.domain.game.admin.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import garlicbears.quiz.domain.common.entity.QLog;
import garlicbears.quiz.domain.game.admin.dto.VisitorCountDto;

public class AdminLogQueryRepositoryImpl implements AdminLogQueryRepository{
	private final JPAQueryFactory queryFactory;

	public AdminLogQueryRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public Page<VisitorCountDto> getDailyVisitors(Pageable pageable) {
		DateTemplate<String> dateTemplate = Expressions.dateTemplate(
			String.class,
			"date_format({0}, '%Y-%m-%d')",
			QLog.log.createdAt);

		return getVisitorsCount(pageable, dateTemplate);
	}

	@Override
	public Page<VisitorCountDto> getWeeklyVisitors(Pageable pageable) {
		DateTemplate<String> dateTemplate = Expressions.dateTemplate(
			String.class,
			"date_format({0}, '%Y-%u')",
			QLog.log.createdAt);

		return getVisitorsCount(pageable, dateTemplate);
	}

	@Override
	public Page<VisitorCountDto> getMonthlyVisitors(Pageable pageable) {
		DateTemplate<String> dateTemplate = Expressions.dateTemplate(
			String.class,
			"date_format({0}, '%Y-%m')",
			QLog.log.createdAt);

		return getVisitorsCount(pageable, dateTemplate);
	}

	private Page<VisitorCountDto> getVisitorsCount(Pageable pageable, DateTemplate<String> dateTemplate) {
		QLog log = QLog.log;

		List<VisitorCountDto> results = queryFactory
			.select(Projections.constructor(VisitorCountDto.class,
				dateTemplate.as("visitDate"),
				log.user.userId.countDistinct().as("dailyVisitors")
			))
			.from(log)
			.groupBy(dateTemplate)
			.orderBy(dateTemplate.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 전체 결과의 총 개수 계산
		Long total = queryFactory
			.select(dateTemplate.countDistinct())
			.from(log)
			.fetchOne();

		return new PageImpl<>(results, pageable, total == null ? 0 : total);
	}

	public long getDailyActiveUserCount(){
		QLog log = QLog.log;

		LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay();
		LocalDateTime tomorrow = today.plusDays(1);

		Long result = queryFactory
			.select(log.user.userId.countDistinct())
			.from(log)
			.where(log.createdAt.between(today, tomorrow))
			.fetchOne();

		return result == null ? 0 : result;
	}
}
