package garlicbears.quiz.domain.game.user.dto;

/**
 * 사용자가 문제를 푼 후 응답하는 DTO
 */

public class ResponseUserAnswerDto {
	private long topicId;
	private String topicTitle;
	private String imageUrl;

	private long totalQuestions;

	private int userHeartsCount;

	private boolean getBadge;

	public ResponseUserAnswerDto() {
	}

	public ResponseUserAnswerDto(Long topicId, String topicTitle, String imageUrl, long totalQuestions,
		int userHeartsCount, boolean getBadge) {
		this.topicId = topicId;
		this.topicTitle = topicTitle;
		this.imageUrl = imageUrl;
		this.totalQuestions = totalQuestions;
		this.userHeartsCount = userHeartsCount;
		this.getBadge = getBadge;
	}

	public long getTopicId() {
		return topicId;
	}

	public String getTopicTitle() {
		return topicTitle;
	}

	public String getImageUrl() {
		return imageUrl;
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
