package garlicbears._quiz.game.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import garlicbears._quiz.common.entity.Topic;

public interface GameUserTopicRepository extends JpaRepository<Topic, Long>, GameUserTopicQuery {
	List<Topic> findByTopicTitle(String topicTitle);

	Topic findByTopicId(Long topicId);


}
