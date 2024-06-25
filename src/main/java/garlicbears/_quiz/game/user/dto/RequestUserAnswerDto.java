package garlicbears._quiz.game.user.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

/**
 * 사용자가 제출한 답안을 받는 DTO
 */

public class RequestUserAnswerDto {

	@NotNull
	private Long topicId;

	@NotNull
	private Long sessionId;

	@NotNull
	private Integer heartsCount;

	@NotNull
	private List<UserAnswerDto> answers;

	public RequestUserAnswerDto() {
	}

	public RequestUserAnswerDto(Long topicId, Long sessionId, int heartsCount, List<UserAnswerDto> answers) {
		this.topicId = topicId;
		this.sessionId = sessionId;
		this.heartsCount = heartsCount;
		this.answers = answers;
	}

	public Long getTopicId() {
		return topicId;
	}

	public Long getSessionId() {
		return sessionId;
	}

	public int getHeartsCount() {
		return heartsCount;
	}

	public List<UserAnswerDto> getAnswers() {
		return answers;
	}
}
