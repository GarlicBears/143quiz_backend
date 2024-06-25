package garlicbears._quiz.management.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import garlicbears._quiz.common.entity.Rating;
import garlicbears._quiz.common.entity.User;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>, RatingQueryRepository {
	Optional<Rating> findByUser(User user);
}
