package garlicbears.quiz.domain.game.admin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import garlicbears.quiz.domain.game.admin.dto.ResponseTopicDto;
import garlicbears.quiz.domain.game.common.entity.QQuestion;
import garlicbears.quiz.domain.game.common.entity.QTopic;

@Repository
public class AdminTopicQueryRepositoryImpl implements AdminTopicQueryRepository {
	private final JPAQueryFactory queryFactory;

	public AdminTopicQueryRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public Page<ResponseTopicDto> findTopics(int page, int size, String sortBy, Pageable pageable) {
		QTopic topic = QTopic.topic;
		QQuestion question = QQuestion.question;

		List<ResponseTopicDto> results = queryFactory
			.select(Projections.constructor(ResponseTopicDto.class,
				topic.topicId,
				topic.topicTitle,
				topic.topicActive,
				topic.createdAt,
				topic.updatedAt,
				topic.topicUsageCount,
				Expressions.as(
					queryFactory
						.select(question.count())
						.from(question)
						.where(question.topic.eq(topic)),
					"questionCount"
				)
			))
			.from(topic)
			.orderBy(getOrderSpecifier(topic, sortBy))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = queryFactory
			.select(topic.count())
			.from(topic)
			.fetchOne();

		return new PageImpl<>(results, pageable, total == null ? 0 : total);
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
}
