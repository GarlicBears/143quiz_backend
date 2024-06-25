package garlicbears._quiz.game.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import garlicbears._quiz.common.entity.Reward;
import garlicbears._quiz.common.entity.Topic;
import garlicbears._quiz.common.entity.User;

@Repository
public interface GameUserRewardRepository extends JpaRepository<Reward, Long> {
	Optional<Reward> findByUserAndTopic(User user, Topic topic);

}
