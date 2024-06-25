package garlicbears._quiz.game.user.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import garlicbears._quiz.common.entity.Active;
import garlicbears._quiz.common.entity.AnswerStatus;
import garlicbears._quiz.common.entity.QQuestion;
import garlicbears._quiz.common.entity.QUserAnswer;
import garlicbears._quiz.game.user.dto.GameStartQuestionDto;
import jakarta.persistence.EntityManager;

@Repository
public class GameUserQuestionQueryImpl implements GameUserQuestionQuery {
  private final JPAQueryFactory queryFactory;

  public GameUserQuestionQueryImpl(EntityManager em) {
      this.queryFactory = new JPAQueryFactory(em);
  }


  //게임 시작 시 사용자에게 보여줄 문제 정보를 가져온다.
  @Override
  public List<GameStartQuestionDto> findGameQuestion(Long topicId, Long userId){
      QQuestion question = QQuestion.question;
      QUserAnswer userAnswer = QUserAnswer.userAnswer;

      //서브쿼리를 사용하여 사용자가 이미 푼 문제는 제외한다.
      QUserAnswer userAnswer2 = new QUserAnswer("userAnswer2");
      BooleanExpression notExistsSubQuery = Expressions.asBoolean(
          JPAExpressions.selectOne()
              .from(userAnswer2)
              .where(userAnswer2.user.userId.eq(userId)
                  .and(userAnswer2.question.questionId.eq(
                      question.questionId))
                  .and(userAnswer2.topic.topicId.eq(question.topic.topicId))
                  .and(userAnswer2.userAnswerStatus.eq(AnswerStatus.Y)))
              .notExists()
      );

      //해당 토픽의 문제 중 사용자가 풀지 않은 문제를 가져온다.
      BooleanExpression conditions = question.topic.topicId.eq(topicId)
          .and(question.questionActive.eq(Active.active))
          .and(userAnswer.question.questionId.isNull()
              .or(userAnswer.userAnswerStatus.eq(AnswerStatus.N).or(
                  userAnswer.userAnswerStatus.eq(AnswerStatus.P)
              )))
          .and(notExistsSubQuery);

      int limit = 10;

      return queryFactory
          .select(Projections.constructor(GameStartQuestionDto.class,
              question.questionId,
              question.questionText,
              question.questionAnswerText
          ))
          .from(question)
          .leftJoin(userAnswer)
          .on(question.questionId.eq(userAnswer.question.questionId)
              .and(question.topic.topicId.eq(userAnswer.topic.topicId))
              .and(userAnswer.user.userId.eq(userId)))
          .where(conditions)
          .distinct()
          .limit(limit)
          .fetch();
  }
}
