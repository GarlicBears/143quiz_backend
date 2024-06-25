package garlicbears._quiz.game.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import garlicbears._quiz.common.entity.Question;

public interface AdminQuestionRepository extends JpaRepository<Question, Long>, AdminQuestionQuery {

	List<Question> findByQuestionAnswerText(String questionAnswerText);

	Optional<Question> findByQuestionId(Long questionId);
}
