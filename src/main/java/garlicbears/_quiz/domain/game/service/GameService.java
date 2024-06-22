package garlicbears._quiz.domain.game.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import garlicbears._quiz.domain.game.dto.ResponseGameStartDto;
import garlicbears._quiz.domain.game.dto.TopicsListDto;
import garlicbears._quiz.domain.game.entity.GameSession;
import garlicbears._quiz.domain.game.entity.Topic;
import garlicbears._quiz.domain.game.repository.GameSessionRepository;
import garlicbears._quiz.domain.game.repository.QuestionRepository;
import garlicbears._quiz.domain.game.repository.RewardRepository;
import garlicbears._quiz.domain.game.repository.TopicRepository;
import garlicbears._quiz.domain.user.dto.UserRankingDto;
import garlicbears._quiz.domain.user.entity.User;
import garlicbears._quiz.domain.user.repository.UserRepository;
import garlicbears._quiz.global.exception.CustomException;
import garlicbears._quiz.global.exception.ErrorCode;

@Service
public class GameService {
	private static final Logger logger = Logger.getLogger(GameService.class.getName());
	private final UserRepository userRepository;
	private final GameSessionRepository gameSessionRepository;
	private final QuestionRepository questionRepository;
	private final TopicRepository topicRepository;
	private final RewardRepository rewardRepository;

	public GameService(UserRepository userRepository, GameSessionRepository gameSessionRepository,
		QuestionRepository questionRepository, TopicRepository topicRepository,
		RewardRepository rewardRepository) {
		this.userRepository = userRepository;
		this.gameSessionRepository = gameSessionRepository;
		this.questionRepository = questionRepository;
		this.topicRepository = topicRepository;
		this.rewardRepository = rewardRepository;
	}

	public List<UserRankingDto> getRankings(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		return userRepository.findRankings(pageable).getContent();
	}

	public List<UserRankingDto> getRankingsByTopicId(long topicId, int pageNumber, int pageSize) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		return userRepository.findRankingsByTopicId(topicId, pageable).getContent();
	}

	@Transactional
	public List<TopicsListDto> topicList(long userId) {
		return topicRepository.findUnacquaintedBadgeTopicsByUser(userId);
	}

	@Transactional
	public List<TopicsListDto> badgeList(long userId) {
		return topicRepository.findTopicsWithBadgeByUser(userId);
	}

	@Transactional
	public ResponseGameStartDto gameStart(long topicId, User user) {
		Random random = new Random();
		long newTopicId = topicId;
		if (topicId == 0) {
			List<TopicsListDto> userTopicList = topicRepository.findUnacquaintedBadgeTopicsByUser(user.getUserId());

			if (userTopicList.isEmpty()) {
				throw new CustomException(ErrorCode.UNKNOWN_TOPIC);
			} else {
				int size = userTopicList.size();
				int index = random.nextInt(size);
				TopicsListDto topicsListDto = userTopicList.get(index);
				newTopicId = topicsListDto.getTopicId();
			}
		}

		logger.info(" newTopic Id : " + newTopicId);
		Topic topic = Optional.of(topicRepository.findByTopicId(newTopicId))
			.orElseThrow(() -> {
				if (topicId == 0) {
					return new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
				} else {
					return new CustomException(ErrorCode.UNKNOWN_TOPIC);
				}
			});

		GameSession gameSession = new GameSession(user, topic);
		gameSessionRepository.save(gameSession);

		return new ResponseGameStartDto.ResponseGameStartBuilder()
			.topicId(newTopicId)
			.sessionId(gameSession.getGameSessionId())
			.game(questionRepository.findGameQuestion(newTopicId, user.getUserId()))
			.build();
	}
}
