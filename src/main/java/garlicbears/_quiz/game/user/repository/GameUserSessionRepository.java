package garlicbears._quiz.game.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import garlicbears._quiz.common.entity.GameSession;
import garlicbears._quiz.common.entity.User;

@Repository
public interface GameUserSessionRepository extends JpaRepository<GameSession, Long> {

	//게임 세션 ID와 사용자로 게임 세션을 찾는다.
	Optional<GameSession> findByGameSessionIdAndUser(Long gameSessionId, User user);
}
