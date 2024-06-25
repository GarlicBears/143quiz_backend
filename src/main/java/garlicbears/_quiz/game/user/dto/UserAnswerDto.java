package garlicbears._quiz.game.user.dto;


import garlicbears._quiz.common.entity.AnswerStatus;
import jakarta.validation.constraints.NotNull;

/**
 * 사용자가 문제를 푼 후 응답하는 DTO
 */

public class UserAnswerDto {
	@NotNull
	private Long questionId;

	@NotNull
	private String answerText;

	@NotNull
	private AnswerStatus answerStatus;

	@NotNull
	private Integer hintUsageCount;

	@NotNull
	private Integer answerTimeTaken;

	@NotNull
	private String answerAt;

	public UserAnswerDto() {
	}

	public UserAnswerDto(Long questionId, String answerText, AnswerStatus answerStatus, int hintUsageCount,
		int answerTimeTaken, String answerAt) {
		this.questionId = questionId;
		this.answerText = answerText;
		this.answerStatus = answerStatus;
		this.hintUsageCount = hintUsageCount;
		this.answerTimeTaken = answerTimeTaken;
		this.answerAt = answerAt;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public String getAnswerText() {
		return answerText;
	}

	public AnswerStatus getAnswerStatus() {
		return answerStatus;
	}

	public int getHintUsageCount() {
		return hintUsageCount;
	}

	public int getAnswerTimeTaken() {
		return answerTimeTaken;
	}

	public String getAnswerAt() {
		return answerAt;
	}
}
