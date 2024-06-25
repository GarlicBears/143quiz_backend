package garlicbears._quiz.common.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "topics")
public class Topic extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "topic_seq")
	private Long topicId;

	@Column(name = "topic_title", nullable = false, length = 100)
	private String topicTitle;

	@Enumerated(EnumType.STRING)
	@Column(name = "topic_active", nullable = false)
	private Active topicActive = Active.active;

	@Column(name = "topic_usage_count", nullable = false)
	private int topicUsageCount;

	@OneToMany(mappedBy = "topic")
	private List<Question> question = new ArrayList<>();

	@OneToMany(mappedBy = "topic")
	private List<Reward> reward = new ArrayList<>();

	public Topic() {
	}

	public Topic(String topicTitle) {
		this.topicTitle = topicTitle;
		this.topicActive = Active.active;
		this.topicUsageCount = 0;
	}

	public Topic(String topicTitle, Active topicActive, int topicUsageCount) {
		this.topicTitle = topicTitle;
		this.topicActive = topicActive;
		this.topicUsageCount = topicUsageCount;
	}

	public Long getTopicId() {
		return topicId;
	}

	public String getTopicTitle() {
		return topicTitle;
	}

	public Active getTopicActive() {
		return topicActive;
	}

	public int getTopicUsageCount() {
		return topicUsageCount;
	}

	public List<Question> getQuestion() {
		return question;
	}

	public List<Reward> getReward() {
		return reward;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	public void setTopicActive(Active topicActive) {
		this.topicActive = topicActive;
	}

	public void setTopicUsageCount(int topicUsageCount) {
		this.topicUsageCount = topicUsageCount;
	}
}
