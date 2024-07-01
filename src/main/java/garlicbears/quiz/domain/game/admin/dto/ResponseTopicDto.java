package garlicbears.quiz.domain.game.admin.dto;

import java.time.LocalDateTime;

import garlicbears.quiz.domain.common.entity.Active;
import garlicbears.quiz.domain.game.common.entity.Topic;

public class ResponseTopicDto {
	private long topicId;
	private String topicText;
	private String topicStatus;
	private LocalDateTime topicCreationDate;
	private LocalDateTime topicUpdateDate;
	private int topicUsageCount;
	private long topicQuestionCount;

	public ResponseTopicDto(long topicId, String topicText, Active topicStatus, LocalDateTime topicCreationDate,
		LocalDateTime topicUpdateDate, int topicUsageCount, long topicQuestionCount) {
		this.topicId = topicId;
		this.topicText = topicText;
		this.topicStatus = topicStatus.toString();
		this.topicCreationDate = topicCreationDate;
		this.topicUpdateDate = topicUpdateDate;
		this.topicUsageCount = topicUsageCount;
		this.topicQuestionCount = topicQuestionCount;
	}

	public long getTopicId() {
		return topicId;
	}

	public String getTopicText() {
		return topicText;
	}

	public String getTopicStatus() {
		return topicStatus;
	}

	public LocalDateTime getTopicCreationDate() {
		return topicCreationDate;
	}

	public LocalDateTime getTopicUpdateDate() {
		return topicUpdateDate;
	}

	public int getTopicUsageCount() {
		return topicUsageCount;
	}

	public long getTopicQuestionCount() {
		return topicQuestionCount;
	}
}
