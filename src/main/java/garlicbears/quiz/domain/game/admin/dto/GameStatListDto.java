package garlicbears.quiz.domain.game.admin.dto;

import java.util.List;

public class GameStatListDto {
	private String sort;
	private int pageNumber;
	private int pageSize;
	private int totalPage;
	private long totalCount;
	private List<GameStatDto> stats;

	public GameStatListDto(String sort, int pageNumber, int pageSize, int totalPage, long totalCount,
		List<GameStatDto> stats) {
		this.sort = sort;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.totalCount = totalCount;
		this.stats = stats;
	}

	public String getSort() {
		return sort;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public List<GameStatDto> getStats() {
		return stats;
	}
}
