package garlicbears._quiz.management.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import garlicbears._quiz.common.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUserId(long userId);

	List<User> findByUserEmail(String email);

	List<User> findByUserNickname(String nickname);
}
