package garlicbears._quiz.common.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@Table(name = "game_sessions")
@DynamicInsert
@DynamicUpdate
public class GameSession {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "game_session_seq")
	private long gameSessionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_seq")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "topic_seq")
	Topic topic;

	@Column(name = "game_started_at", nullable = false)
	@ColumnDefault("CURRENT_TIMESTAMP")
	private LocalDateTime gameStartTime;

	@Column(name = "game_ended_at")
	private LocalDateTime gameEndTime;

	@Column(name = "game_dropout", nullable = false)
	private boolean gameDropout;

	@Column(name = "game_hearts_count", nullable = false)
	@ColumnDefault("0")
	private int heartsCount;

	public long getGameSessionId() {
		return gameSessionId;
	}

	public User getUser() {
		return user;
	}

	public Topic getTopic() {
		return topic;
	}

	public LocalDateTime getGameStartTime() {
		return gameStartTime;
	}

	public LocalDateTime getGameEndTime() {
		return gameEndTime;
	}

	public boolean getGameDropout() {
		return gameDropout;
	}

	public int getHeartsCount() {
		return heartsCount;
	}

	public void setGameEndTime(LocalDateTime gameEndTime) {
		this.gameEndTime = gameEndTime;
	}

	public void setGameDropout(boolean gameDropout) {
		this.gameDropout = gameDropout;
	}

	public void setHeartsCount(int heartsCount) {
		this.heartsCount = heartsCount;
	}

	protected GameSession() {
	}

	public GameSession(User user, Topic topic) {
		this.user = user;
		this.topic = topic;
		this.gameDropout = true;
	}

}
