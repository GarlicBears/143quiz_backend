package garlicbears._quiz.management.user.dto;

public class RatingStatDto {
	private double averageRating;
	private long ratingCount;

	public RatingStatDto(double averageRating, long ratingCount) {
		this.averageRating = averageRating;
		this.ratingCount = ratingCount;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public long getRatingCount() {
		return ratingCount;
	}
}
