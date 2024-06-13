package garlicbears._quiz.domain.game.dto;

import garlicbears._quiz.domain.game.entity.Topic;
import garlicbears._quiz.global.entity.Active;

import java.time.LocalDateTime;

public class ResponseTopicDto {
    private long topicId;
    private String topicText;
    private String topicStatus;
    private LocalDateTime topicCreationDate;
    private LocalDateTime topicUpdateDate;
    private int topicUsageCount;

    public ResponseTopicDto(long topicId, String topicText, Active topicStatus, LocalDateTime topicCreationDate, LocalDateTime topicUpdateDate, int topicUsageCount) {
        this.topicId = topicId;
        this.topicText = topicText;
        this.topicStatus = topicStatus.toString();
        this.topicCreationDate = topicCreationDate;
        this.topicUpdateDate = topicUpdateDate;
        this.topicUsageCount = topicUsageCount;
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

    public static ResponseTopicDto fromTopic(Topic topic) {
        return new ResponseTopicDto(
                topic.getTopicSeq(),
                topic.getTopicTitle(),
                topic.getTopicActive(),
                topic.getCreatedAt(),
                topic.getUpdatedAt(),
                topic.getTopicUsageCount());
    }
}
