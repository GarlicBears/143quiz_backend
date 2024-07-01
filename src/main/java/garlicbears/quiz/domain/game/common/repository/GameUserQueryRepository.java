package garlicbears.quiz.domain.game.common.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import garlicbears.quiz.domain.game.user.dto.UserRankingDto;

public interface GameUserQueryRepository {
	Page<UserRankingDto> findRankings(Pageable pageable);

	Page<UserRankingDto> findRankingsByTopicId(long topicId, Pageable pageable);
}
