package garlicbears._quiz.domain.admin.dto;

import java.util.List;

import garlicbears._quiz.domain.game.dto.ResponseQuestionDto;
import garlicbears._quiz.domain.game.entity.Topic;

public class TopicExcelUploadedDto {
	private long topicId;
	private String topicTitle;
	private int questionCount;
	private List<ResponseQuestionDto> questions;

	public TopicExcelUploadedDto(Topic topic, List<ResponseQuestionDto> questions) {
		this.topicId = topic.getTopicId();
		this.topicTitle = topic.getTopicTitle();
		this.questions = questions;
		this.questionCount = questions.size();
	}

	public int getQuestionCount() {
		return questionCount;
	}

	public long getTopicId() {
		return topicId;
	}

	public String getTopicTitle() {
		return topicTitle;
	}

	public List<ResponseQuestionDto> getQuestions() {
		return questions;
	}
}
