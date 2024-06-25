package garlicbears._quiz.management.common.dto;

import garlicbears._quiz.common.entity.Active;
import garlicbears._quiz.common.entity.Gender;
import garlicbears._quiz.common.entity.Location;
import garlicbears._quiz.common.entity.User;

public class ResponseUserDto {
	private long userId;
	private String email;
	private String nickname;
	private int birthYear;
	private int age;
	private String gender;
	private String location;
	private String active;

	public ResponseUserDto(long userId, String email, String nickname, int birthYear, int age, Gender gender,
		Location location, Active active) {
		this.userId = userId;
		this.email = email;
		this.nickname = nickname;
		this.birthYear = birthYear;
		this.age = age;
		this.gender = gender.getKoreanName();
		this.location = location.getKoreanName();
		this.active = active.name();
	}

	public long getUserId() {
		return userId;
	}

	public String getEmail() {
		return email;
	}

	public String getNickname() {
		return nickname;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public int getAge() {
		return age;
	}

	public String getGender() {
		return gender;
	}

	public String getLocation() {
		return location;
	}

	public String getActive() {
		return active;
	}

	public static ResponseUserDto fromUser(User user) {
		return new ResponseUserDto(user.getUserId(), user.getUserEmail(), user.getUserNickname(),
			user.getUserBirthYear(), user.getUserAge(), user.getUserGender(),
			user.getUserLocation(), user.getUserActive());
	}
}