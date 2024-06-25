package garlicbears._quiz.game.user.dto;

/**
 * 사용자가 문제를 푼 후 응답하는 DTO
 */

public class ResponseUserAnswerDto {

	private long totalQuestions;

	private int userHeartsCount;

	private boolean getBadge;

	public ResponseUserAnswerDto() {
	}

	public ResponseUserAnswerDto(long totalQuestions,
		int userHeartsCount, boolean getBadge) {
		this.totalQuestions = totalQuestions;
		this.userHeartsCount = userHeartsCount;
		this.getBadge = getBadge;
	}

	public long getTotalQuestions() {
		return totalQuestions;
	}

	public int getUserHeartsCount() {
		return userHeartsCount;
	}

	public boolean isGetBadge() {
		return getBadge;
	}
}
