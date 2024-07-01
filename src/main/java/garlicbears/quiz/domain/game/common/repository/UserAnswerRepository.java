package garlicbears.quiz.domain.game.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import garlicbears.quiz.domain.game.admin.repository.AdminUserAnswerQueryRepository;
import garlicbears.quiz.domain.game.common.entity.UserAnswer;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long>, AdminUserAnswerQueryRepository {
}
