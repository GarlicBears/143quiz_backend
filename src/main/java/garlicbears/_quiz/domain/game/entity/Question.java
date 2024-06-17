package garlicbears._quiz.domain.game.entity;

import garlicbears._quiz.global.entity.Active;
import garlicbears._quiz.global.entity.BaseTimeEntity;
import jakarta.persistence.*;

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

    public Question(){}

    public Question(Topic topic, String questionText, String questionAnswerText) {
        this.topic = topic;
        this.questionText = questionText;
        this.questionAnswerText = questionAnswerText;
        this.questionActive = Active.active;
    }

    public Long getQuestionId(){
        return questionId;
    }

    public Topic getTopic(){
        return topic;
    }

    public String getQuestionText(){
        return questionText;
    }

    public String getQuestionAnswerText(){
        return questionAnswerText;
    }

    public Active getQuestionActive(){
        return questionActive;
    }

}
