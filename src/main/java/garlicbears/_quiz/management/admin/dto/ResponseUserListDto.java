package garlicbears._quiz.management.admin.dto;

import java.util.List;

import garlicbears._quiz.management.common.dto.ResponseUserDto;

public class ResponseUserListDto {
	private String sort;
	private int pageNumber;
	private int pageSize;
	private int totalPage;
	private long totalCount;
	private List<ResponseUserDto> users;

	public ResponseUserListDto(String sort, int pageNumber, int pageSize, int totalPage, long totalCount,
		List<ResponseUserDto> users) {
		this.sort = sort;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.totalCount = totalCount;
		this.users = users;
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

	public List<ResponseUserDto> getUsers() {
		return users;
	}
}
