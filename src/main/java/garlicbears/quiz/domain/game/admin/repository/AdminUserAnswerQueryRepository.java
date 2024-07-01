package garlicbears.quiz.domain.game.admin.repository;

import garlicbears.quiz.domain.game.admin.dto.TopicPlayTimeDto;

public interface AdminUserAnswerQueryRepository {
	TopicPlayTimeDto getPlayTime(long topicId);
}
