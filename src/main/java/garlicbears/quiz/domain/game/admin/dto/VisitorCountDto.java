package garlicbears.quiz.domain.game.admin.dto;

public class VisitorCountDto {
	private String date;
	private long count;

	public VisitorCountDto(String date, long count) {
		this.date = date;
		this.count = count;
	}

	public String getDate() {
		return date;
	}

	public long getCount() {
		return count;
	}
}
