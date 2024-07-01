package garlicbears.quiz.domain.game.admin.dto;

import java.util.List;

public class VisitorCountListDto {
	private int pageNumber;
	private int pageSize;
	private int totalPage;
	private long totalCount;
	private List<VisitorCountDto> visitors;

	public VisitorCountListDto(int pageNumber, int pageSize, int totalPage, long totalCount,
		List<VisitorCountDto> visitors) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.totalCount = totalCount;
		this.visitors = visitors;
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

	public List<VisitorCountDto> getVisitors() {
		return visitors;
	}
}
