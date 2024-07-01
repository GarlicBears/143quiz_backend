package garlicbears.quiz.domain.game.common.repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import garlicbears.quiz.domain.common.entity.QUser;
import garlicbears.quiz.domain.game.common.entity.QReward;
import garlicbears.quiz.domain.game.user.dto.UserRankingDto;
import jakarta.persistence.EntityManager;

public class GameUserQueryRepositoryImpl implements GameUserQueryRepository {
	private final JPAQueryFactory queryFactory;

	public GameUserQueryRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<UserRankingDto> findRankings(Pageable pageable) {
		// 뱃지 개수 > 하트 개수 순으로 랭킹 조회
		QUser user = QUser.user;
		QReward reward = QReward.reward;

		List<UserRankingDto> results = queryFactory.select(
				Projections.constructor(UserRankingDto.class, user.userId, user.userNickname,
					reward.rewardBadgeStatus.when(true).then(1).otherwise(0).sum().as("totalBadges"),
					reward.rewardNumberHearts.sum().as("totalHearts"), Expressions.constant(0L)))
			.from(reward)
			.join(reward.user, user)
			.groupBy(user.userId, user.userNickname)
			.orderBy(reward.rewardBadgeStatus.when(true).then(1).otherwise(0).sum().desc(),
				reward.rewardNumberHearts.sum().desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 전체 결과 수 계산
		Long total = queryFactory.select(reward.user.userId.countDistinct())
			.from(reward)
			.fetchOne();

		// Calculate rank and add it to each UserRankingDTO
		return getUserRankingDtos(pageable, results, total);
	}

	private Page<UserRankingDto> getUserRankingDtos(Pageable pageable, List<UserRankingDto> results, Long total) {
		List<UserRankingDto> rankedResults = IntStream.range(0, results.size()).mapToObj(i -> {
			UserRankingDto dto = results.get(i);
			return new UserRankingDto(dto.getUserId(), dto.getNickname(), dto.getTotalBadges(), dto.getTotalHearts(),
				pageable.getOffset() + i + 1L // Calculate rank based on page offset
			);
		}).collect(Collectors.toList());

		return new PageImpl<>(rankedResults, pageable, total == null ? 0 : total);
	}

	@Override
	public Page<UserRankingDto> findRankingsByTopicId(long topicId, Pageable pageable) {
		// 특정 주제에 대한 뱃지는 1개 뿐이기 때문에 하트 개수로 랭킹 조회
		QUser user = QUser.user;
		QReward reward = QReward.reward;

		List<UserRankingDto> results = queryFactory.select(
				Projections.constructor(UserRankingDto.class, user.userId, user.userNickname,
					reward.rewardBadgeStatus.when(true).then(1).otherwise(0).sum().as("totalBadges"),
					reward.rewardNumberHearts.sum().as("totalHearts"), Expressions.constant(0L)))
			.from(reward)
			.join(reward.user, user)
			.where(reward.topic.topicId.eq(topicId))
			.groupBy(user.userId, user.userNickname)
			.orderBy(reward.rewardNumberHearts.sum().desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = queryFactory.select(user.userId.countDistinct())
			.from(reward)
			.where(reward.topic.topicId.eq(topicId))
			.fetchOne();

		// Calculate rank and add it to each UserRankingDTO
		return getUserRankingDtos(pageable, results, total == null ? 0 : total);
	}
}
