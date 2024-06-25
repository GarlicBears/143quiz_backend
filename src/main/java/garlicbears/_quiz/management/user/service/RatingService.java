package garlicbears._quiz.management.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import garlicbears._quiz.common.entity.Rating;
import garlicbears._quiz.common.entity.User;
import garlicbears._quiz.common.exception.CustomException;
import garlicbears._quiz.common.exception.ErrorCode;
import garlicbears._quiz.management.user.dto.RatingStatDto;
import garlicbears._quiz.management.user.repository.RatingRepository;

@Service
public class RatingService {
	private final RatingRepository ratingRepository;

	@Autowired
	public RatingService(RatingRepository ratingRepository) {
		this.ratingRepository = ratingRepository;
	}

	public RatingStatDto getRatingStat() {
		// 별점 평균 반환
		Double averageRating = ratingRepository.getAverageRating();
		if (averageRating == null) {
			return new RatingStatDto(0, 0);
		}
		return new RatingStatDto(averageRating, ratingRepository.count());
	}

	public void saveRating(User user, Double ratingValue) {
		// 이미 평가한 사용자인지 확인
		ratingRepository.findByUser(user).ifPresent(rating -> {
			throw new CustomException(ErrorCode.ALREADY_RATED);
		});

		// 별점 값이 0~5 사이인지 확인
		if (ratingValue == null || ratingValue < 0 || ratingValue > 5) {
			throw new CustomException(ErrorCode.ILLEGAL_RATING_VALUE);
		}

		ratingRepository.save(new Rating(user, ratingValue));
	}
}
