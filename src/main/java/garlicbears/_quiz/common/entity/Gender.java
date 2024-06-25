package garlicbears._quiz.common.entity;

import garlicbears._quiz.common.exception.CustomException;
import garlicbears._quiz.common.exception.ErrorCode;

public enum Gender {
	male("남자"), female("여자"), other("기타");

	private final String koreanName;

	Gender(String koreanName) {
		this.koreanName = koreanName;
	}

	public String getKoreanName() {
		return koreanName;
	}

	public static Gender fromKoreanName(String koreanName) {
		for (Gender gender : Gender.values()) {
			if (gender.getKoreanName().equalsIgnoreCase(koreanName)) {
				return gender;
			}
		}
		throw new CustomException(ErrorCode.UNKNOWN_GENDER);
	}
}
