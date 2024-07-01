package garlicbears.quiz.domain.game.admin.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import garlicbears.quiz.domain.game.admin.dto.TopicPlayTimeDto;
import garlicbears.quiz.domain.game.common.entity.QGameSession;
import garlicbears.quiz.domain.game.common.entity.QUserAnswer;

public class AdminUserAnswerQueryRepositoryImpl implements AdminUserAnswerQueryRepository{
	private final JPAQueryFactory queryFactory;

	public AdminUserAnswerQueryRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public TopicPlayTimeDto getPlayTime(long topicId) {
		QUserAnswer userAnswer = QUserAnswer.userAnswer;
		QGameSession gameSession = QGameSession.gameSession;

		return queryFactory
			.select(Projections.constructor(TopicPlayTimeDto.class,
				userAnswer.topic.topicId,
				gameSession.gameSessionId.countDistinct().as("totalGames"),
				userAnswer.timeTaken.sum().as("totalTimeTaken"))
			)
			.from(userAnswer)
			.join(gameSession).on(userAnswer.gameSession.gameSessionId.eq(gameSession.gameSessionId))
			.where(
				gameSession.gameDropout.eq(false)
				.and(userAnswer.topic.topicId.eq(topicId))
			)
			.groupBy(userAnswer.topic.topicId)
			.fetchOne();
	}
}
