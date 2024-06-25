package garlicbears._quiz.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "questions")
public class Question extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_seq")
	private Long questionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "topic_seq")
	private Topic topic;

	@Column(name = "question_text", nullable = false, length = 100)
	private String questionText;

	@Column(name = "question_answer_text", nullable = false, length = 100)
	private String questionAnswerText;

	@Enumerated(EnumType.STRING)
	@Column(name = "question_active", nullable = false)
	private Active questionActive;

	public Question() {
	}

	public Question(Topic topic, String questionText, String questionAnswerText) {
		this.topic = topic;
		this.questionText = questionText;
		this.questionAnswerText = questionAnswerText;
		this.questionActive = Active.active;
	}

	public Question(Long questionId, Topic topic, String questionText, String questionAnswerText,
		Active questionActive) {
		this.questionId = questionId;
		this.topic = topic;
		this.questionText = questionText;
		this.questionAnswerText = questionAnswerText;
		this.questionActive = questionActive;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public Topic getTopic() {
		return topic;
	}

	public String getQuestionText() {
		return questionText;
	}

	public String getQuestionAnswerText() {
		return questionAnswerText;
	}

	public Active getQuestionActive() {
		return questionActive;
	}

	public void setQuestionAnswerText(String questionAnswerText) {
		this.questionAnswerText = questionAnswerText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public void setQuestionActive(Active questionActive) {
		this.questionActive = questionActive;
	}
}
