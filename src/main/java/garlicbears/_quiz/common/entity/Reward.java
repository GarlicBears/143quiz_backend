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
@Table(name = "rewards")
@DynamicInsert
@DynamicUpdate
public class Reward {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reward_seq", nullable = false)
	private Long rewardId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_seq")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "topic_seq")
	private Topic topic;

	@Column(name = "reward_number_hearts", nullable = false)
	@ColumnDefault("0")
	private int rewardNumberHearts;

	@Column(name = "reward_badge_status", nullable = false)
	@ColumnDefault("FALSE")
	private boolean rewardBadgeStatus = false;

	@Column(name = "reward_badge_created_at")
	private LocalDateTime rewardBadgeCreatedAt;

	public Reward() {
	}

	public Reward(User user, Topic topic, int rewardNumberHearts) {
		this.user = user;
		this.topic = topic;
		this.rewardNumberHearts = rewardNumberHearts;
		this.rewardBadgeCreatedAt = null;
	}

	public Long getRewardId() {
		return rewardId;
	}

	public User getUser() {
		return user;
	}

	public Topic getTopic() {
		return topic;
	}

	public int getRewardNumberHearts() {
		return rewardNumberHearts;
	}

	public void setRewardNumberHearts(int rewardNumberHearts) {
		this.rewardNumberHearts = rewardNumberHearts;
	}

	public boolean getRewardBadgeStatus() {
		return rewardBadgeStatus;
	}

	public void setRewardBadgeStatus(boolean rewardBadgeStatus) {
		this.rewardBadgeStatus = rewardBadgeStatus;
	}

	public LocalDateTime getRewardBadgeCreatedAt() {
		return rewardBadgeCreatedAt;
	}

	public void setRewardBadgeCreatedAt(LocalDateTime rewardBadgeCreatedAt) {
		this.rewardBadgeCreatedAt = rewardBadgeCreatedAt;
	}
}

