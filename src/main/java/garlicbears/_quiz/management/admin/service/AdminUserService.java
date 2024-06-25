package garlicbears._quiz.management.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import garlicbears._quiz.common.entity.Active;
import garlicbears._quiz.common.entity.User;
import garlicbears._quiz.common.exception.CustomException;
import garlicbears._quiz.common.exception.ErrorCode;
import garlicbears._quiz.management.admin.dto.ResponseUserListDto;
import garlicbears._quiz.management.admin.repository.AdminUserRepository;
import garlicbears._quiz.management.common.dto.ResponseUserDto;

@Service
public class AdminUserService {
	private final AdminUserRepository adminUserRepository;

	@Autowired
	AdminUserService(AdminUserRepository adminUserRepository) {
		this.adminUserRepository = adminUserRepository;
	}

	public ResponseUserListDto getUserList(int pageNumber, int pageSize, String sort) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<ResponseUserDto> page = adminUserRepository.findUsers(pageNumber, pageSize, sort, pageable);

		return new ResponseUserListDto(sort, pageNumber, pageSize, page.getTotalPages(), page.getTotalElements(),
			page.getContent());
	}

	@Transactional
	public void delete(long userId) {
		User user = adminUserRepository.findByUserId(userId)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		user.setUserActive(Active.inactive);
		adminUserRepository.save(user);
	}

}
