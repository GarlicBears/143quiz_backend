package garlicbears.quiz.domain.game.common.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import garlicbears.quiz.domain.common.repository.UserRepository;
import garlicbears.quiz.domain.game.user.dto.UserRankingDto;

@Service
public class RankingService {
	private final UserRepository userRepository;

	public RankingService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<UserRankingDto> getRankings(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		return userRepository.findRankings(pageable).getContent();
	}

	public List<UserRankingDto> getRankingsByTopicId(long topicId, int pageNumber, int pageSize) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		return userRepository.findRankingsByTopicId(topicId, pageable).getContent();
	}
}
