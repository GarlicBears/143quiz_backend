package garlicbears._quiz.game.user.dto;

/**
 * 게임 시작 시 사용자에게 보여줄 문제 정보를 담는 DTO
 */

public class GameStartQuestionDto {

	private long questionId;

	private String questionText;

	private String answerText;

	public long getQuestionId() {
		return questionId;
	}

	public String getQuestionText() {
		return questionText;
	}

	public String getAnswerText() {
		return answerText;
	}

	public GameStartQuestionDto() {
	}

	public GameStartQuestionDto(long questionId, String questionText, String answerText) {
		this.questionId = questionId;
		this.questionText = questionText;
		this.answerText = answerText;
	}

}
