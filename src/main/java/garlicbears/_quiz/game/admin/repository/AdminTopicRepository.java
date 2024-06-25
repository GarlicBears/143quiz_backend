package garlicbears._quiz.game.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import garlicbears._quiz.common.entity.Topic;

public interface AdminTopicRepository extends JpaRepository<Topic, Long>, AdminTopicQuery {
	List<Topic> findByTopicTitle(String topicTitle);

}
