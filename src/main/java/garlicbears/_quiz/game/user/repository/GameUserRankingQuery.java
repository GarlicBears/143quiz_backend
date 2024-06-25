package garlicbears._quiz.game.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import garlicbears._quiz.game.user.dto.GameUserRankingDto;

public interface GameUserRankingQuery {
	
	Page<GameUserRankingDto> findRankings(Pageable pageable);

	Page<GameUserRankingDto> findRankingsByTopicId(long topicId, Pageable pageable);
}
