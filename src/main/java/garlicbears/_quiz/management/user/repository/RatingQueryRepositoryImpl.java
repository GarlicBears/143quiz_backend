package garlicbears._quiz.management.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import garlicbears._quiz.common.entity.QRating;
import jakarta.persistence.EntityManager;

public class RatingQueryRepositoryImpl implements RatingQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public RatingQueryRepositoryImpl(EntityManager em) {
		this.jpaQueryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Double getAverageRating() {
		QRating rating = QRating.rating;

		return jpaQueryFactory.select(rating.ratingValue.avg().as("averageRating")).from(rating).fetchOne();
	}
}
