package garlicbears._quiz.domain.game.entity;

import garlicbears._quiz.global.entity.Active;
import garlicbears._quiz.global.entity.BaseTimeEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    public Topic(){}

    public Topic(String topicTitle){
        this.topicTitle = topicTitle;
        this.topicActive = Active.active;
        this.topicUsageCount = 0;
    }

    public Topic(String topicTitle, Active topicActive, int topicUsageCount){
        this.topicTitle = topicTitle;
        this.topicActive = topicActive;
        this.topicUsageCount = topicUsageCount;
    }

    public Long getTopicId(){
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
}
