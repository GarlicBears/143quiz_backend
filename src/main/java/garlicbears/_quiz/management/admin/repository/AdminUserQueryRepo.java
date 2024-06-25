package garlicbears._quiz.management.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import garlicbears._quiz.management.common.dto.ResponseUserDto;

public interface AdminUserQueryRepo {
	Page<ResponseUserDto> findUsers(int page, int size, String sortBy, Pageable pageable);

}
