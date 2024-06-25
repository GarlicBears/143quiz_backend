package garlicbears._quiz.management.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import garlicbears._quiz.common.entity.User;

@Repository
public interface AdminUserRepository extends JpaRepository<User, Long>, AdminUserQueryRepo{
	Optional<User> findByUserId(long userId);
}

