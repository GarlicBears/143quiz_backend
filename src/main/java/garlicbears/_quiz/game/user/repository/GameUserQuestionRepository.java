package garlicbears._quiz.game.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import garlicbears._quiz.common.entity.Active;
import garlicbears._quiz.common.entity.Question;
import garlicbears._quiz.common.entity.Topic;

public interface GameUserQuestionRepository extends JpaRepository<Question, Long>, GameUserQuestionQuery {

	Optional<Question> findByQuestionId(Long questionId);

	//주어진 주제와 활성 상태에 해당하는 모든 문제의 수를 반환한다.
	Long countAllByTopicAndQuestionActive(Topic topic, Active active);
}
