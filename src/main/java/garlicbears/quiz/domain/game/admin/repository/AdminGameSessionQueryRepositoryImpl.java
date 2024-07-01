package garlicbears.quiz.domain.game.admin.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import garlicbears.quiz.domain.game.admin.dto.GameStatDto;
import garlicbears.quiz.domain.game.common.entity.QGameSession;
import garlicbears.quiz.domain.game.common.entity.QTopic;

public class AdminGameSessionQueryRepositoryImpl implements AdminGameSessionQueryRepository{
	private final JPAQueryFactory queryFactory;

	public AdminGameSessionQueryRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public Page<GameStatDto> calculateGameStat(String sort, Pageable pageable) {
		QGameSession gameSession = QGameSession.gameSession;
		QTopic topic = QTopic.topic;

		List<GameStatDto> results = queryFactory
			.select(Projections.constructor(GameStatDto.class,
				gameSession.topic.topicId,
				topic.topicTitle,
				gameSession.gameSessionId.count().as("totalPlayCount"),
				gameSession.heartsCount.sum().as("totalCorrectCount")
			))
			.from(gameSession)
			.leftJoin(gameSession.topic, topic)
			.groupBy(gameSession.topic.topicId)
			.orderBy(getOrderSpecifier(topic, sort))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = queryFactory
			.select(gameSession.topic.topicId.countDistinct())
			.from(gameSession)
			.fetchOne();

		return new PageImpl<>(results, pageable, total == null ? 0 : total);
	}

	private OrderSpecifier<?> getOrderSpecifier(QTopic topic, String sort) {
		return switch (sort) {
			case "titleAsc" -> topic.topicTitle.asc();
			case "titleDesc" -> topic.topicTitle.desc();
			case "createdAtAsc" -> topic.createdAt.asc();
			case "createdAtDesc" -> topic.createdAt.desc();
			case "updatedAtAsc" -> topic.updatedAt.asc();
			case "updatedAtDesc" -> topic.updatedAt.desc();
			case "usageCountAsc" -> topic.topicUsageCount.asc();
			case "usageCountDesc" -> topic.topicUsageCount.desc();
			default -> topic.topicId.desc();
		};
	}

	public long getTodayGameSessionCount() {
		QGameSession gameSession = QGameSession.gameSession;

		LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay();
		LocalDateTime tomorrow = today.plusDays(1);

		Long result = queryFactory
			.select(gameSession.gameSessionId.count())
			.from(gameSession)
			.where(gameSession.gameStartTime.between(today, tomorrow))
			.fetchOne();

		return result == null ? 0 : result;
	}
}
