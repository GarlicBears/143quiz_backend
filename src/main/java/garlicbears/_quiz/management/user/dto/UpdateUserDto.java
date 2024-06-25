package garlicbears._quiz.management.user.dto;

public class UpdateUserDto {
	private Integer birthYear;
	private String gender;
	private String location;

	public UpdateUserDto(Integer birthYear, String gender, String location) {
		this.birthYear = birthYear;
		this.gender = gender;
		this.location = location;
	}

	public Integer getBirthYear() {
		return birthYear;
	}

	public String getGender() {
		return gender;
	}

	public String getLocation() {
		return location;
	}
}
