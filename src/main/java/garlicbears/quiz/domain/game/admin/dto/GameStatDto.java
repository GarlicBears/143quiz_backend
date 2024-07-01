package garlicbears.quiz.domain.game.admin.dto;

public class GameStatDto {
	private long topicId;
	private String title;
	private long usageCount;
	private long completeCount;
	private int totalCorrectCount;
	private int totalPlayTime;
	private double averageCompletePlayRate;
	private double averagePlayTime;
	private double averageCorrectionRate;

	public GameStatDto(long topicId, String title, long usageCount, int totalCorrectCount) {
		this.topicId = topicId;
		this.title = title;
		this.usageCount = usageCount;
		this.completeCount = 0;
		this.totalCorrectCount = totalCorrectCount;
		this.totalPlayTime = 0;
	}

	public void calculate()
	{
		if (usageCount == 0 || completeCount == 0)
		{
			averageCompletePlayRate = 0;
			averagePlayTime = 0;
			averageCorrectionRate = 0;
		}
		else
		{
			averageCompletePlayRate = (double) 100 * completeCount / usageCount;
			averagePlayTime = (double) totalPlayTime / completeCount;
			averageCorrectionRate = (double) 100 * totalCorrectCount / (completeCount * 10);
		}
	}

	public long getTopicId() {
		return topicId;
	}

	public String getTitle() {
		return title;
	}

	public long getUsageCount() {
		return usageCount;
	}

	public long getCompleteCount() {
		return completeCount;
	}

	public void setCompleteCount(long completeCount) {
		this.completeCount = completeCount;
	}

	public int getTotalCorrectCount() {
		return totalCorrectCount;
	}

	public int getTotalPlayTime() {
		return totalPlayTime;
	}

	public void setTotalPlayTime(int totalPlayTime) {
		this.totalPlayTime = totalPlayTime;
	}

	public void setCompleteCountAndTotalPlayTime(TopicPlayTimeDto rhs)
	{
		this.completeCount = rhs.getCompleteCount();
		this.totalPlayTime = rhs.getTotalPlayTime();
	}

	public double getAverageCompletePlayRate() {
		return averageCompletePlayRate;
	}

	public double getAveragePlayTime() {
		return averagePlayTime;
	}

	public double getAverageCorrectionRate() {
		return averageCorrectionRate;
	}
}
