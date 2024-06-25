package garlicbears._quiz.game.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import garlicbears._quiz.common.entity.Topic;
import garlicbears._quiz.game.admin.dto.ResponseQuestionDto;

public interface AdminQuestionQuery {
	Page<ResponseQuestionDto> findQuestions(int pageNumber, int pageSize, String sort, Pageable pageable);

	Page<ResponseQuestionDto> findQuestionsByTopicId(long topicId, int pageNumber, int pageSize, String sort,
		Pageable pageable);

	void deleteByTopic(Topic topic);
}
