package garlicbears._quiz.game.admin.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import garlicbears._quiz.common.entity.QTopic;
import garlicbears._quiz.game.admin.dto.ResponseTopicDto;
import jakarta.persistence.EntityManager;

@Repository
public class AdminTopicQueryImpl implements AdminTopicQuery {
	private final JPAQueryFactory queryFactory;

	@Autowired
	public AdminTopicQueryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<ResponseTopicDto> findTopics(int page, int size, String sortBy, Pageable pageable) {
		QTopic topic = QTopic.topic;

		List<ResponseTopicDto> results = queryFactory
			.select(Projections.constructor(ResponseTopicDto.class,
				topic.topicId,
				topic.topicTitle,
				topic.topicActive,
				topic.createdAt,
				topic.updatedAt,
				topic.topicUsageCount
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
