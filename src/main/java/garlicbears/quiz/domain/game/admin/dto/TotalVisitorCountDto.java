package garlicbears.quiz.domain.game.admin.dto;

public class TotalVisitorCountDto {
	private long totalVisitors;

	public TotalVisitorCountDto(long totalVisitors) {
		this.totalVisitors = totalVisitors;
	}

	public long getTotalVisitors() {
		return totalVisitors;
	}
}
