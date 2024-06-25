package garlicbears._quiz.game.admin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import garlicbears._quiz.common.entity.Active;
import garlicbears._quiz.common.entity.QQuestion;
import garlicbears._quiz.common.entity.Topic;
import garlicbears._quiz.game.admin.dto.ResponseQuestionDto;
import jakarta.persistence.EntityManager;

@Repository
public class AdminQuestionQueryImpl implements AdminQuestionQuery {
  private final JPAQueryFactory queryFactory;

  public AdminQuestionQueryImpl(EntityManager em) {
      this.queryFactory = new JPAQueryFactory(em);
  }

  private OrderSpecifier<?> getOrderSpecifier(QQuestion question, String sort) {
      return switch (sort) {
          case "questionTextAsc" -> question.questionText.asc();
          case "questionTextDesc" -> question.questionText.desc();
          case "createdAtAsc" -> question.createdAt.asc();
          case "createdAtDesc" -> question.createdAt.desc();
          case "updatedAtAsc" -> question.updatedAt.asc();
          case "updatedAtDesc" -> question.updatedAt.desc();
          default -> question.questionId.asc();
      };
  }

  private JPAQuery<ResponseQuestionDto> createBaseQuery(QQuestion question, String sort) {
      return queryFactory
              .select(Projections.constructor(ResponseQuestionDto.class,
                      question.topic.topicId,
                      question.topic.topicTitle,
                      question.questionId,
                      question.questionText,
                      question.questionAnswerText,
                      question.questionActive,
                      question.createdAt,
                      question.updatedAt
              ))
              .from(question)
              .orderBy(getOrderSpecifier(question, sort));
  }

  @Override
  public Page<ResponseQuestionDto> findQuestions(int pageNumber, int pageSize, String sort, Pageable pageable) {
      QQuestion question = QQuestion.question;
      JPAQuery<ResponseQuestionDto> baseQuery = createBaseQuery(question, sort);

      List<ResponseQuestionDto> results = baseQuery
              .offset(pageable.getOffset())
              .limit(pageable.getPageSize())
              .fetch();

      Long total = queryFactory
              .select(question.count())
              .from(question)
              .fetchOne();

      return new PageImpl<>(results, pageable, total == null ? 0 : total);
  }

  @Override
  public Page<ResponseQuestionDto> findQuestionsByTopicId(long topicId, int pageNumber, int pageSize, String sort, Pageable pageable) {
      QQuestion question = QQuestion.question;
      JPAQuery<ResponseQuestionDto> baseQuery = createBaseQuery(question, sort)
              .where(question.topic.topicId.eq(topicId));

      List<ResponseQuestionDto> results = baseQuery
              .offset(pageable.getOffset())
              .limit(pageable.getPageSize())
              .fetch();

      Long total = queryFactory
              .select(question.count())
              .from(question)
              .where(question.topic.topicId.eq(topicId))
              .fetchOne();

      return new PageImpl<>(results, pageable, total == null ? 0 : total);
  }

  @Override
  public void deleteByTopic(Topic topic) {
      QQuestion question = QQuestion.question;
      queryFactory
              .update(question)
              .set(question.questionActive, Active.inactive)
              .where(question.topic.eq(topic))
              .execute();
  }

}
