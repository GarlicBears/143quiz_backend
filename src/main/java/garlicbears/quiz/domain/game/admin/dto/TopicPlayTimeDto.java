package garlicbears.quiz.domain.game.admin.dto;

public class TopicPlayTimeDto {
	private long topicId;
	private long completeCount;
	private int totalPlayTime;

	public TopicPlayTimeDto(long topicId, long completeCount, int totalPlayTime) {
		this.topicId = topicId;
		this.completeCount = completeCount;
		this.totalPlayTime = totalPlayTime;
	}

	public long getTopicId() {
		return topicId;
	}

	public long getCompleteCount() {
		return completeCount;
	}

	public int getTotalPlayTime() {
		return totalPlayTime;
	}
}
