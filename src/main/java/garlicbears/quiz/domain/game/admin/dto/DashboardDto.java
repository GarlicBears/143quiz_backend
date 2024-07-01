package garlicbears.quiz.domain.game.admin.dto;

public class DashboardDto {
	private long totalVisitors;
	private long dailyActiveUsers;
	private long dailyGamePlays;
	private long totalUsers;
	private double averageRating;

	public DashboardDto(long totalVisitors, long dailyActiveUsers, long dailyGamePlays, long totalUsers,
		double averageRating) {
		this.totalVisitors = totalVisitors;
		this.dailyActiveUsers = dailyActiveUsers;
		this.dailyGamePlays = dailyGamePlays;
		this.totalUsers = totalUsers;
		this.averageRating = averageRating;
	}

	public long getTotalVisitors() {
		return totalVisitors;
	}

	public long getDailyActiveUsers() {
		return dailyActiveUsers;
	}

	public long getDailyGamePlays() {
		return dailyGamePlays;
	}

	public long getTotalUsers() {
		return totalUsers;
	}

	public double getAverageRating() {
		return averageRating;
	}
}
