package garlicbears._quiz.common.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_answers")
@DynamicInsert
public class UserAnswer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_answer_seq")
	private long userAnswerId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_seq")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_session_seq")
	private GameSession gameSession;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "topic_seq")
	private Topic topic;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_seq")
	private Question question;

	@Column(name = "user_answer_text", nullable = false, length = 100)
	private String userAnswerText;

  @Column(name = "user_answer_status", nullable = false)
  private AnswerStatus userAnswerStatus;

	@Column(name = "hint_usage_count", nullable = false)
	@ColumnDefault("0")
	private int hintUsageCount;

  @Column(name = "answer_time_taken_sec", nullable = false)
  @ColumnDefault("0")
  private int timeTaken;

	@Column(name = "user_answered_at", nullable = false)
	@CreationTimestamp
	@ColumnDefault("CURRENT_TIMESTAMP")
	private LocalDateTime userAnsweredAt;

	public long getUserAnswerId() {
		return userAnswerId;
	}

	public User getUser() {
		return user;
	}

	public GameSession getGameSession() {
		return gameSession;
	}

	public Topic getTopic() {
		return topic;
	}

	public Question getQuestion() {
		return question;
	}

	public String getUserAnswerText() {
		return userAnswerText;
	}

  public AnswerStatus getUserAnswerStatus() {
      return userAnswerStatus;
  }

	public int getHintUsageCount() {
		return hintUsageCount;
	}

	public int getTimeTaken() {
		return timeTaken;
	}

	public LocalDateTime getUserAnsweredAt() {
		return userAnsweredAt;
	}

	protected UserAnswer() {
	}

	private UserAnswer(UserAnswerBuilder builder) {
		this.gameSession = builder.gameSession;
		this.topic = builder.topic;
		this.user = builder.user;
		this.question = builder.question;
		this.userAnswerText = builder.userAnswerText;
		this.userAnswerStatus = builder.userAnswerStatus;
		this.hintUsageCount = builder.hintUsageCount;
		this.timeTaken = builder.timeTaken;
		this.userAnsweredAt = builder.userAnsweredAt;
	}

	public static class UserAnswerBuilder {
		private final GameSession gameSession;

		private final User user;

		private final Topic topic;

		private Question question;

		private String userAnswerText;

    private AnswerStatus userAnswerStatus;

		private int hintUsageCount;

		private int timeTaken;

		private LocalDateTime userAnsweredAt;

		public UserAnswerBuilder(User user, GameSession gameSession, Topic topic) {
			this.gameSession = gameSession;
			this.user = user;
			this.topic = topic;
		}

		public UserAnswerBuilder question(Question question) {
			this.question = question;
			return this;
		}

		public UserAnswerBuilder userAnswerText(String userAnswerText) {
			this.userAnswerText = userAnswerText;
			return this;
		}

    public UserAnswerBuilder userAnswerStatus(AnswerStatus userAnswerStatus) {
        this.userAnswerStatus = userAnswerStatus;
        return this;
    }

		public UserAnswerBuilder hintUsageCount(int hintUsageCount) {
			this.hintUsageCount = hintUsageCount;
			return this;
		}

		public UserAnswerBuilder timeTaken(int timeTaken) {
			this.timeTaken = timeTaken;
			return this;
		}

		public UserAnswerBuilder userAnsweredAt(LocalDateTime userAnsweredAt) {
			this.userAnsweredAt = userAnsweredAt;
			return this;
		}

		public UserAnswer build() {
			return new UserAnswer(this);
		}
	}

}
