package garlicbears._quiz.game.user.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import garlicbears._quiz.common.entity.Active;
import garlicbears._quiz.common.entity.QReward;
import garlicbears._quiz.common.entity.QTopic;
import garlicbears._quiz.game.user.dto.TopicsListDto;
import jakarta.persistence.EntityManager;

@Repository
public class GameUserTopicQueryImpl implements GameUserTopicQuery {
	private final JPAQueryFactory queryFactory;

	@Autowired
	public GameUserTopicQueryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}


	private OrderSpecifier<?> getOrderSpecifier(QTopic topic, String sortBy) {
		return switch (sortBy) {
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

	@Override
	public List<TopicsListDto> findUnacquaintedBadgeTopicsByUser(long userId) {
		QTopic topic = QTopic.topic;
		QReward reward = QReward.reward;

		return queryFactory
			.select(Projections.constructor(TopicsListDto.class,
				topic.topicId,
				topic.topicTitle,
				reward.rewardNumberHearts
			))
			.from(topic)
			.leftJoin(reward)
			.on(topic.topicId.eq(reward.topic.topicId)
				.and(reward.user.userId.eq(userId)))
			.where(topic.topicActive.eq(Active.active)
				.and((reward.topic.topicId.isNull()
					.or(reward.rewardBadgeStatus.eq(false)))))
			.fetch();

	}

	@Override
	public List<TopicsListDto> findTopicsWithBadgeByUser(long userId) {
		QTopic topic = QTopic.topic;
		QReward reward = QReward.reward;

		return queryFactory
			.select(Projections.constructor(TopicsListDto.class,
				topic.topicId,
				topic.topicTitle
			))
			.from(topic)
			.join(reward)
			.on(topic.topicId.eq(reward.topic.topicId)
				.and(reward.user.userId.eq(userId)))
			.where(reward.rewardBadgeStatus.eq(true)
				.and(topic.topicActive.eq(Active.active)))
			.fetch();

	}

}
