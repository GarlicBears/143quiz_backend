package garlicbears._quiz.game.user.dto;

import java.util.List;

/**
 * 게임 시작 시 사용자에게 보여줄 문제 정보를 담는 DTO
 */

public class ResponseGameStartDto {

	private long topicId;

	private long sessionId;

	private List<GameStartQuestionDto> game;

	public ResponseGameStartDto() {
	}

	public ResponseGameStartDto(ResponseGameStartBuilder builder) {
		this.topicId = builder.topicId;
		this.sessionId = builder.sessionId;
		this.game = builder.game;
	}

	public long getTopicId() {
		return topicId;
	}

	public long getSessionId() {
		return sessionId;
	}

	public List<GameStartQuestionDto> getGame() {
		return game;
	}

	public static class ResponseGameStartBuilder {

		private long topicId;

		private long sessionId;

		private List<GameStartQuestionDto> game;

		public ResponseGameStartBuilder topicId(long topicId) {
			this.topicId = topicId;
			return this;
		}

		public ResponseGameStartBuilder sessionId(long sessionId) {
			this.sessionId = sessionId;
			return this;
		}

		public ResponseGameStartBuilder game(List<GameStartQuestionDto> game) {
			this.game = game;
			return this;
		}

		public ResponseGameStartDto build() {
			return new ResponseGameStartDto(this);
		}

	}
}
