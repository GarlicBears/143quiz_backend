package garlicbears._quiz.management.admin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import garlicbears._quiz.common.entity.QUser;
import garlicbears._quiz.management.common.dto.ResponseUserDto;
import jakarta.persistence.EntityManager;

public class AdminUserQueryRepoImpl implements AdminUserQueryRepo {
	private final JPAQueryFactory queryFactory;

	public AdminUserQueryRepoImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	private OrderSpecifier<?> getOrderSpecifier(QUser user, String sortBy) {
		return switch (sortBy) {
			case "nicknameAsc" -> user.userNickname.asc();
			case "nicknameDesc" -> user.userNickname.desc();
			case "ageAsc" -> user.userAge.asc();
			case "ageDesc" -> user.userAge.desc();
			case "createdAtAsc" -> user.createdAt.asc();
			case "createdAtDesc" -> user.createdAt.desc();
			default -> user.userId.asc();
		};
	}

	@Override
	public Page<ResponseUserDto> findUsers(int page, int size, String sortBy, Pageable pageable) {
		QUser user = QUser.user;

		List<ResponseUserDto> results = queryFactory
			.select(Projections.constructor(ResponseUserDto.class,
				user.userId,
				user.userEmail,
				user.userNickname,
				user.userBirthYear,
				user.userAge,
				user.userGender,
				user.userLocation,
				user.userActive
			))
			.from(user)
			.orderBy(getOrderSpecifier(user, sortBy))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = queryFactory
			.select(user.count())
			.from(user)
			.fetchOne();

		return new PageImpl<>(results, pageable, total == null ? 0 : total);
	}


}
