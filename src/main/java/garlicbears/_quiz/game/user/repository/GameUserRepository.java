package garlicbears._quiz.game.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import garlicbears._quiz.common.entity.User;

@Repository
public interface GameUserRepository extends JpaRepository<User, Long>, GameUserRankingQuery {

}
