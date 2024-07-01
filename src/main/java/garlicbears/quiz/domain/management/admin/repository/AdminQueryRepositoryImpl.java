package garlicbears.quiz.domain.management.admin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import garlicbears.quiz.domain.common.entity.QAdmin;
import garlicbears.quiz.domain.management.admin.dto.ResponseAdminDto;
import jakarta.persistence.EntityManager;

public class AdminQueryRepositoryImpl implements AdminQueryRepository {
	private final JPAQueryFactory queryFactory;

	public AdminQueryRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	private OrderSpecifier<?> getOrderSpecifier(QAdmin admin, String sortBy) {
		return switch (sortBy) {
			case "createdAtAsc" -> admin.createdAt.asc();
			case "createdAtDesc" -> admin.createdAt.desc();
			default -> admin.adminId.asc();
		};
	}

	@Override
	public Page<ResponseAdminDto> findAdmins(int page, int size, String sortBy, Pageable pageable) {
		QAdmin admin = QAdmin.admin;

		List<ResponseAdminDto> results = queryFactory
			.select(Projections.constructor(ResponseAdminDto.class,
				admin.adminId,
				admin.adminEmail,
				admin.active,
				admin.createdAt,
				admin.updatedAt
			))
			.from(admin)
			.orderBy(getOrderSpecifier(admin, sortBy))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = queryFactory
			.select(admin.count())
			.from(admin)
			.fetchOne();

		return new PageImpl<>(results, pageable, total == null ? 0 : total);
	}
}
