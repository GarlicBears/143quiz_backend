package garlicbears.quiz.domain.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import garlicbears.quiz.domain.common.entity.Log;
import garlicbears.quiz.domain.game.admin.repository.AdminLogQueryRepository;

public interface LogRepository extends JpaRepository<Log, Long>, AdminLogQueryRepository {
}
