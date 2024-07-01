package garlicbears.quiz.domain.game.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import garlicbears.quiz.domain.common.entity.User;
import garlicbears.quiz.domain.game.admin.repository.AdminGameSessionQueryRepository;
import garlicbears.quiz.domain.game.common.entity.GameSession;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long>,
	AdminGameSessionQueryRepository {

	//게임 세션 ID와 사용자로 게임 세션을 찾는다.
	Optional<GameSession> findByGameSessionIdAndUser(Long gameSessionId, User user);
}
