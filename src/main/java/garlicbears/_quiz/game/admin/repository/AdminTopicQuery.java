package garlicbears._quiz.game.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import garlicbears._quiz.game.admin.dto.ResponseTopicDto;

public interface AdminTopicQuery {
	Page<ResponseTopicDto> findTopics(int page, int size, String sortBy, Pageable pageable);

}
