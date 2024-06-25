package garlicbears._quiz.game.admin.dto;

import java.util.List;

public class ResponseQuestionListDto {
	private String sort;
	private int pageNumber;
	private int pageSize;
	private int totalPage;
	private long totalCount;
	private List<ResponseQuestionDto> questions;

	public String getSort() {
		return sort;
	}

	public ResponseQuestionListDto(List<ResponseQuestionDto> questions, String sort, int pageNumber, int pageSize,
		int totalPage, long totalCount) {
		this.sort = sort;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.totalCount = totalCount;
		this.questions = questions;
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

	public List<ResponseQuestionDto> getQuestions() {
		return questions;
	}
}
