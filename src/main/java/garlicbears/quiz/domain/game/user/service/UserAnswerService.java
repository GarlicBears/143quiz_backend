package garlicbears.quiz.domain.game.user.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import garlicbears.quiz.domain.common.entity.Active;
import garlicbears.quiz.domain.common.entity.User;
import garlicbears.quiz.domain.game.common.entity.GameSession;
import garlicbears.quiz.domain.game.common.entity.Question;
import garlicbears.quiz.domain.game.common.entity.Reward;
import garlicbears.quiz.domain.game.common.entity.Topic;
import garlicbears.quiz.domain.game.common.entity.UserAnswer;
import garlicbears.quiz.domain.game.common.repository.GameSessionRepository;
import garlicbears.quiz.domain.game.common.repository.QuestionRepository;
import garlicbears.quiz.domain.game.common.repository.RewardRepository;
import garlicbears.quiz.domain.game.common.repository.TopicRepository;
import garlicbears.quiz.domain.game.user.dto.RequestUserAnswerDto;
import garlicbears.quiz.domain.game.user.dto.ResponseUserAnswerDto;
import garlicbears.quiz.domain.game.user.dto.UserAnswerDto;
import garlicbears.quiz.global.exception.CustomException;
import garlicbears.quiz.global.exception.ErrorCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class UserAnswerService {

	private static final Logger logger = Logger.getLogger(UserAnswerService.class.getName());

	private final RewardRepository rewardRepository;

	private final GameSessionRepository gameSessionRepository;

	private final QuestionRepository questionRepository;

	private final TopicRepository topicRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	UserAnswerService(RewardRepository rewardRepository, GameSessionRepository gameSessionRepository,
		QuestionRepository questionRepository, TopicRepository topicRepository) {
		this.rewardRepository = rewardRepository;
		this.gameSessionRepository = gameSessionRepository;
		this.questionRepository = questionRepository;
		this.topicRepository = topicRepository;
	}

	/**
	 * 사용자가 문제를 맞추었을 때 보상을 업데이트한다.
	 */
	@Transactional
	public ResponseUserAnswerDto rewardUpdate(User user, RequestUserAnswerDto requestUserAnswerDto) {
		// 주제 정보 조회
		Topic topic = Optional.of(topicRepository.findByTopicId(requestUserAnswerDto.getTopicId()))
			.orElseThrow(() -> new CustomException(ErrorCode.UNKNOWN_TOPIC));

		// 주제에 대한 모든 문제의 수를 가져옴
		long totalQuestions = questionRepository.countAllByTopicAndQuestionActive(topic, Active.active);

		// 사용자가 주제에 대한 보상을 받은 적이 있는지 확인
		Reward reward = rewardRepository.findByUserAndTopic(user, topic)
			.orElseGet(() -> new Reward(user, topic, 0));

		// 보상의 하트 개수를 업데이트
		int updatedHeartsCount = updateRewardHearts(reward, requestUserAnswerDto.getHeartsCount(), totalQuestions);

		// 필요한 경우 뱃지 상태 업데이트
		boolean badgeStatusUpdated = updateBadgeStatusIfNecessary(reward, updatedHeartsCount, totalQuestions);

		// 보상 저장
		rewardRepository.save(reward);

		return new ResponseUserAnswerDto(topic.getTopicId(), topic.getTopicTitle(),
			topic.getTopicImage().getAccessUrl(), totalQuestions, updatedHeartsCount, badgeStatusUpdated);
	}

	/**
	 * 보상의 하트 개수를 업데이트하고 유효성을 검사
	 */
	private int updateRewardHearts(Reward reward, int heartsCount, long totalQuestions) {
		int updatedHeartsCount = reward.getRewardNumberHearts() + heartsCount;
		validateHeartsCount(updatedHeartsCount, totalQuestions);
		reward.setRewardNumberHearts(updatedHeartsCount);
		return updatedHeartsCount;
	}

	/**
	 * 보상의 하트 개수가 모든 문제의 수와 같은 경우 뱃지 상태를 업데이트하는 메서드
	 */
	private boolean updateBadgeStatusIfNecessary(Reward reward, int updatedHeartsCount, long totalQuestions) {
		// 보상의 뱃지 상태가 이미 존재하는지 확인
		validateRewardBadgeStatus(reward.getRewardBadgeStatus());
		if (updatedHeartsCount == totalQuestions) {
			reward.setRewardBadgeStatus(true);
			reward.setRewardBadgeCreatedAt(LocalDateTime.now());
			return true;
		}
		return false;
	}

	/**
	 * 보상의 하트 개수가 문제의 수를 초과하는지 확인
	 */
	private void validateHeartsCount(int updatedHeartsCount, long totalQuestions) {
		if (updatedHeartsCount > totalQuestions) {
			logger.warning("updatedHeartsCount : " + updatedHeartsCount);
			logger.warning("totalQuestions : " + totalQuestions);
			throw new CustomException(ErrorCode.HEARTS_COUNT_EXCEEDS);
		}
	}

	/**
	 * 보상의 뱃지 상태가 이미 존재하는지 확인
	 */
	private void validateRewardBadgeStatus(boolean rewardBadgeStatus) {
		if (rewardBadgeStatus) {
			throw new CustomException(ErrorCode.BADGE_ALREADY_EXISTS);
		}
	}

	/**
	 * 사용자가 문제를 맞추었을 때 사용자의 답변을 저장한다.
	 */
	@Transactional
	public void userAnswerSave(User user, RequestUserAnswerDto requestUserAnswerDto) {
		//날짜 형식을 지정한다.
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		//게임 세션 테이블의 게임 세션 ID와 사용자 ID를 가져온다.
		GameSession gameSession = gameSessionRepository.findByGameSessionIdAndUser(
				requestUserAnswerDto.getSessionId(), user)
			.orElseThrow(() -> new CustomException(ErrorCode.UNKNOWN_GAMESESSION));

		//주제 ID를 가져온다.
		Topic topic = Optional.of(topicRepository.findByTopicId(requestUserAnswerDto.getTopicId()))
			.orElseThrow(() -> new CustomException(ErrorCode.UNKNOWN_TOPIC));

		//사용자의 답변을 가져온다.
		List<UserAnswerDto> answers = requestUserAnswerDto.getAnswers();
		int length = answers.size();

		for (int i = 0; i < length; i++) {
			UserAnswerDto answerDto = answers.get(i);
			boolean isLastAnswer = (i == length - 1);    //마지막 답변인지 확인한다.

			//마지막 답변이라면 게임 세션 테이블의 하트 개수와 게임 종료 여부를 업데이트한다.
			if (isLastAnswer) {
				gameSession.setHeartsCount(requestUserAnswerDto.getHeartsCount());
				gameSession.setGameDropout(false);
				gameSession.setGameEndTime(LocalDateTime.parse(answerDto.getAnswerAt(), formatter));
				gameSessionRepository.save(gameSession);
			}

			Question question = questionRepository.findByQuestionId(answers.get(i).getQuestionId())
				.orElseThrow(() -> new CustomException(ErrorCode.UNKNOWN_QUESTION));

			LocalDateTime parsedDate = LocalDateTime.parse(answerDto.getAnswerAt(), formatter);
			System.out.println("Parsed Date for question " + question.getQuestionId() + ": " + parsedDate);

			UserAnswer userAnswer = new UserAnswer.UserAnswerBuilder(user, gameSession, topic)
				.question(question)
				.userAnswerText(answerDto.getAnswerText())
				.userAnswerStatus(answerDto.getAnswerStatus())
				.hintUsageCount(answerDto.getHintUsageCount())
				.timeTaken(answerDto.getAnswerTimeTaken())
				.userAnsweredAt(parsedDate)
				.build();

			entityManager.persist(userAnswer);
			entityManager.flush();
		}
	}

	/**
	 * 사용자가 비정상적으로 게임을 종료했을 때 게임세션에 저장한다.
	 */
	public void dropGameSession(long sessionId, User user) {
		GameSession gameSession = gameSessionRepository.findByGameSessionIdAndUser(sessionId, user)
			.orElseThrow(() -> new CustomException(ErrorCode.UNKNOWN_GAMESESSION));

		//비정상적으로 종료하면 게임 하트 개수가 저장되지 않으므로
		// 하트 개수가 0을 체크한 후 게임 종료 여부를 업데이트한다.
		if (gameSession.getHeartsCount() == 0) {
			gameSession.setGameDropout(true);
			gameSession.setGameEndTime(LocalDateTime.now());
			gameSessionRepository.save(gameSession);
		} else {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}

	}

}
