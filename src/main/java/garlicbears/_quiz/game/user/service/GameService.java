package garlicbears._quiz.game.user.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import garlicbears._quiz.common.entity.Active;
import garlicbears._quiz.common.entity.GameSession;
import garlicbears._quiz.common.entity.Topic;
import garlicbears._quiz.common.entity.User;
import garlicbears._quiz.common.exception.CustomException;
import garlicbears._quiz.common.exception.ErrorCode;
import garlicbears._quiz.game.user.dto.ResponseGameStartDto;
import garlicbears._quiz.game.user.dto.TopicsListDto;
import garlicbears._quiz.game.user.repository.GameUserQuestionRepository;
import garlicbears._quiz.game.user.repository.GameUserSessionRepository;
import garlicbears._quiz.game.user.repository.GameUserTopicRepository;

@Service
public class GameService {
	private static final Logger logger = Logger.getLogger(GameService.class.getName());
	private final GameUserSessionRepository gameUserSessionRepository;
	private final GameUserQuestionRepository gameUserQuestionRepository;
	private final GameUserTopicRepository gameUserTopicRepository;

	public GameService(GameUserSessionRepository gameUserSessionRepository,
		GameUserQuestionRepository gameUserQuestionRepository, GameUserTopicRepository gameUserTopicRepository) {
		this.gameUserSessionRepository = gameUserSessionRepository;
		this.gameUserQuestionRepository = gameUserQuestionRepository;
		this.gameUserTopicRepository = gameUserTopicRepository;
	}

	@Transactional
	public List<TopicsListDto> topicList(long userId) {
		List<TopicsListDto> topicsList = gameUserTopicRepository.findUnacquaintedBadgeTopicsByUser(userId);
		//주제별 총 문제 수 추가
		for(TopicsListDto dto : topicsList){
			Topic topicEntity = gameUserTopicRepository.findByTopicId(dto.getTopicId());
			long questionCount = gameUserQuestionRepository.countAllByTopicAndQuestionActive(
				topicEntity, Active.active);
			dto.setTotalQuestionsCount(questionCount);
		}
		return topicsList;
	}

	@Transactional
	public List<TopicsListDto> badgeList(long userId) {
		return gameUserTopicRepository.findTopicsWithBadgeByUser(userId);
	}

	@Transactional
	public ResponseGameStartDto gameStart(long topicId, User user) {
		//랜덤 주제 할당
		if (topicId == 0) {
			topicId = randomTopicAssigner(user);
		}

		logger.info(" topicId : " + topicId);
		Topic topic = Optional.of(gameUserTopicRepository.findByTopicId(topicId))
			.orElseThrow(() -> new CustomException(ErrorCode.UNKNOWN_TOPIC));

		GameSession gameSession = new GameSession(user, topic);
		gameUserSessionRepository.save(gameSession);

		return new ResponseGameStartDto.ResponseGameStartBuilder()
			.topicId(topicId)
			.sessionId(gameSession.getGameSessionId())
			.game(gameUserQuestionRepository.findGameQuestion(topicId, user.getUserId()))
			.build();
	}

	/**
	 * 랜덤 주제 할당
	 */
	private long randomTopicAssigner(User user) {
		Random random = new Random();

		List<TopicsListDto> userTopicList = gameUserTopicRepository
			.findUnacquaintedBadgeTopicsByUser(user.getUserId());

		if (userTopicList.isEmpty()) {
			throw new CustomException(ErrorCode.UNKNOWN_TOPIC);
		} else {
			int size = userTopicList.size();
			int index = random.nextInt(size);
			TopicsListDto topicsListDto = userTopicList.get(index);
			return topicsListDto.getTopicId();
		}
	}
}
