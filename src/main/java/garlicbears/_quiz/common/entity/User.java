package garlicbears._quiz.common.entity;

import static garlicbears._quiz.common.entity.Active.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_seq")
	private long userId;

	@Column(name = "user_email", nullable = false, length = 200)
	private String userEmail;

	@Column(name = "user_password", nullable = false, length = 300)
	private String userPassword;

	@Column(name = "user_nickname", nullable = false, length = 100)
	private String userNickname;

	@Column(name = "user_birth_year", nullable = false)
	private int userBirthYear;

	@Column(name = "user_age", nullable = false)
	private int userAge;

	@Enumerated(EnumType.STRING)
	@Column(name = "user_gender", nullable = false)
	private Gender userGender;

	@Enumerated(EnumType.STRING)
	@Column(name = "user_location", nullable = false)
	private Location userLocation;

	@Enumerated(EnumType.STRING)
	@Column(name = "user_active", nullable = false)
	private Active userActive = active;

	@OneToMany(mappedBy = "user")
	private List<Reward> reward = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<UserAnswer> userAnswers = new ArrayList<>();

	public User() {
	}

	public User(UserBuilder builder) {
		this.userEmail = builder.userEmail;
		this.userPassword = builder.userPassword;
		this.userNickname = builder.userNickname;
		this.userBirthYear = builder.userBirthYear;
		this.userAge = builder.userAge;
		this.userGender = builder.userGender;
		this.userLocation = builder.userLocation;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public int getUserBirthYear() {
		return userBirthYear;
	}

	public int getUserAge() {
		return userAge;
	}

	public Gender getUserGender() {
		return userGender;
	}

	public Location getUserLocation() {
		return userLocation;
	}

	public Active getUserActive() {
		return userActive;
	}

	public List<Reward> getRewards() {
		return reward;
	}

	public List<UserAnswer> userAnswers() {
		return userAnswers;
	}

	public void setUserBirthYear(int userBirthYear) {
		this.userBirthYear = userBirthYear;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public void setUserGender(Gender userGender) {
		this.userGender = userGender;
	}

	public void setUserLocation(Location userLocation) {
		this.userLocation = userLocation;
	}

	public void setUserActive(Active userActive) {
		this.userActive = userActive;
	}

	public static class UserBuilder {
		private final String userEmail;
		private final String userPassword;
		private final String userNickname;
		private int userBirthYear;
		private int userAge;
		private Gender userGender;
		private Location userLocation;

		public UserBuilder(String userEmail, String userPassword, String userNickname) {
			this.userEmail = userEmail;
			this.userPassword = userPassword;
			this.userNickname = userNickname;
		}

		public UserBuilder userBirthYear(int userBirthYear) {
			this.userBirthYear = userBirthYear;
			return this;
		}

		public UserBuilder userAge(int userAge) {
			this.userAge = userAge;
			return this;
		}

		public UserBuilder userGender(Gender userGender) {
			this.userGender = userGender;
			return this;
		}

		public UserBuilder userLocation(Location userLocation) {
			this.userLocation = userLocation;
			return this;
		}

		public User build() {
			return new User(this);
		}
	}
}
