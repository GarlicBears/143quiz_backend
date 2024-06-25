package garlicbears._quiz.game.user.dto;

/**
 * 게임 주제 목록 DTO
 */

public class TopicsListDto {

	long topicId;

	String title;

	int heartsCount;

	long totalQuestionsCount;

	public TopicsListDto() {
	}

	public TopicsListDto(long topicId, String title) {
		this.topicId = topicId;
		this.title = title;
	}

	public TopicsListDto(long topicId, String title, int heartsCount) {
		this.topicId = topicId;
		this.title = title;
		this.heartsCount = heartsCount;
	}

	public long getTopicId() {
		return topicId;
	}

	public String getTitle() {
		return title;
	}

	public int getHeartsCount() {
		return heartsCount;
	}

	public long getTotalQuestionsCount() {
		return totalQuestionsCount;
	}

	public void setTotalQuestionsCount(long totalQuestionsCount) {
		this.totalQuestionsCount = totalQuestionsCount;
	}
}
