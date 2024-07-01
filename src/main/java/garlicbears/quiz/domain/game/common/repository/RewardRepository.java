package garlicbears.quiz.domain.game.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import garlicbears.quiz.domain.common.entity.User;
import garlicbears.quiz.domain.game.common.entity.Reward;
import garlicbears.quiz.domain.game.common.entity.Topic;
import jakarta.persistence.LockModeType;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Reward> findByUserAndTopic(User user, Topic topic);

}
