package garlicbears._quiz.management.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 회원가입 DTO
 */

public class SignUpDto {
	@NotNull
	@Size(max = 100)
	private String email;

	@NotNull
	@Size(max = 200)
	private String password;

	@NotNull
	@Size(max = 100)
	private String nickname;

	@NotNull
	private int birthYear;

	@NotNull
	private String gender;

	@NotNull
	private String location;

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getNickname() {
		return nickname;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public String getGender() {
		return gender;
	}

	public String getLocation() {
		return location;
	}
}
