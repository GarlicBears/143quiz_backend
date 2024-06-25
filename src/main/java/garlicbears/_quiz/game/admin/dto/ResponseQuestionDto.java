package garlicbears._quiz.game.admin.dto;

import java.time.LocalDateTime;

import garlicbears._quiz.common.entity.Active;
import garlicbears._quiz.common.entity.Question;
import garlicbears._quiz.common.entity.Topic;

public class ResponseQuestionDto {
	private long topicId;
	private String topicText;
	private long questionId;
	private String questionText;
	private String questionAnswer;
	private Active questionActive;
	private LocalDateTime questionCreationDate;
	private LocalDateTime questionUpdateDate;

	public ResponseQuestionDto(long topicId, String topicText, long questionId, String questionText,
		String questionAnswer, Active questionActive, LocalDateTime questionCreationDate,
		LocalDateTime questionUpdateDate) {
		this.topicId = topicId;
		this.topicText = topicText;
		this.questionId = questionId;
		this.questionText = questionText;
		this.questionAnswer = questionAnswer;
		this.questionActive = questionActive;
		this.questionCreationDate = questionCreationDate;
		this.questionUpdateDate = questionUpdateDate;
	}

	public ResponseQuestionDto(Topic topic, Question question) {
		this.topicId = topic.getTopicId();
		this.topicText = topic.getTopicTitle();
		this.questionId = question.getQuestionId();
		this.questionText = question.getQuestionText();
		this.questionAnswer = question.getQuestionAnswerText();
		this.questionActive = question.getQuestionActive();
		this.questionCreationDate = question.getCreatedAt();
		this.questionUpdateDate = question.getUpdatedAt();
	}

	public long getTopicId() {
		return topicId;
	}

	public String getTopicText() {
		return topicText;
	}

	public long getQuestionId() {
		return questionId;
	}

	public String getQuestionText() {
		return questionText;
	}

	public String getQuestionAnswer() {
		return questionAnswer;
	}

	public Active getQuestionActive() {
		return questionActive;
	}

	public LocalDateTime getQuestionCreationDate() {
		return questionCreationDate;
	}

	public LocalDateTime getQuestionUpdateDate() {
		return questionUpdateDate;
	}
}
