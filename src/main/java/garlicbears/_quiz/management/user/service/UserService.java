package garlicbears._quiz.management.user.service;

import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import garlicbears._quiz.common.entity.Active;
import garlicbears._quiz.common.entity.Gender;
import garlicbears._quiz.common.entity.Location;
import garlicbears._quiz.common.entity.User;
import garlicbears._quiz.common.exception.CustomException;
import garlicbears._quiz.common.exception.ErrorCode;
import garlicbears._quiz.management.user.dto.SignUpDto;
import garlicbears._quiz.management.user.dto.UpdateUserDto;
import garlicbears._quiz.management.user.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void checkDuplicatedEmail(String email) {
		userRepository.findByUserEmail(email).forEach(user -> {
			if (user.getUserActive() == Active.active) {
				throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
			}
		});
	}

	public void checkDuplicatedNickname(String nickname) {
		userRepository.findByUserNickname(nickname).forEach(user -> {
			if (user.getUserActive() == Active.active) {
				throw new CustomException(ErrorCode.NICKNAME_ALREADY_EXISTS);
			}
		});
	}

	@Transactional
	public void signUp(SignUpDto signUpDto) {

		User user = new User.UserBuilder(signUpDto.getEmail(), passwordEncoder.encode(signUpDto.getPassword()),
			signUpDto.getNickname())
			.userBirthYear(signUpDto.getBirthYear())
			.userAge(Year.now().getValue() - signUpDto.getBirthYear())
			.userLocation(Location.fromKoreanName(signUpDto.getLocation()))
			.userGender(Gender.fromKoreanName(signUpDto.getGender()))
			.build();

		userRepository.save(user);
	}

	@Transactional
	public void update(User user, UpdateUserDto updateUserDto) {
		if (updateUserDto.getBirthYear() != null) {
			int birthYear = updateUserDto.getBirthYear();
			if (birthYear > Year.now().getValue()) {
				throw new CustomException(ErrorCode.INVALID_INPUT);
			}
			user.setUserBirthYear(birthYear);
			user.setUserAge(Year.now().getValue() - birthYear);
		}

		if (updateUserDto.getGender() != null) {
			user.setUserGender(Gender.fromKoreanName(updateUserDto.getGender()));
		}

		if (updateUserDto.getLocation() != null) {
			user.setUserLocation(Location.fromKoreanName(updateUserDto.getLocation()));
		}

		userRepository.save(user);
	}

	@Transactional
	public void delete(User user) {
		user.setUserActive(Active.inactive);
		userRepository.save(user);
	}
}
