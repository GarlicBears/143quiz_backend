package garlicbears._quiz.game.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import garlicbears._quiz.common.entity.Topic;
import garlicbears._quiz.game.user.dto.GameUserRankingDto;
import garlicbears._quiz.game.user.repository.GameUserRepository;
import garlicbears._quiz.game.user.repository.GameUserTopicRepository;

@Service
public class RankingService {
	private final GameUserRepository gameUserRepository;

	private final GameUserTopicRepository gameUserTopicRepository;

	public RankingService(GameUserRepository gameUserRepository,
		GameUserTopicRepository gameUserTopicRepository) {
		this.gameUserRepository = gameUserRepository;
		this.gameUserTopicRepository = gameUserTopicRepository;
	}

	public List<GameUserRankingDto> getRankings(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		return gameUserRepository.findRankings(pageable).getContent();
	}

	public List<GameUserRankingDto> getRankingsByTopicId(long topicId, int pageNumber, int pageSize) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		return gameUserRepository.findRankingsByTopicId(topicId, pageable).getContent();
	}

	public Optional<Topic> findByTopicId(long topicId) {
		return gameUserTopicRepository.findById(topicId);
	}
}
