package garlicbears._quiz.game.admin.dto;

import java.util.List;

public class ResponseTopicListDto {
	private String sort;
	private int pageNumber;
	private int pageSize;
	private int totalPage;
	private long totalCount;
	private List<ResponseTopicDto> topics;

	public ResponseTopicListDto(String sort, int pageNumber, int pageSize, int totalPage, long totalCount,
		List<ResponseTopicDto> topics) {
		this.sort = sort;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.totalCount = totalCount;
		this.topics = topics;
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

	public List<ResponseTopicDto> getTopics() {
		return topics;
	}

	public static ResponseTopicListDto fromTopics(List<ResponseTopicDto> topics, String sort, int pageNumber,
		int pageSize, int totalPage, long totalCount) {
		return new ResponseTopicListDto(sort, pageNumber, pageSize, totalPage, totalCount, topics);
	}
}
