package garlicbears.quiz.domain.management.admin.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import garlicbears.quiz.domain.common.entity.Active;

public class ResponseAdminDto {
	private long adminId;
	private String adminEmail;
	private String adminActive;
	private String createdAt;
	private String updatedAt;

	private List<String> adminRole;

	public ResponseAdminDto(long adminId, String adminEmail, Active adminActive,
		LocalDateTime createdAt, LocalDateTime updatedAt, List<String> adminRole) {
		this.adminId = adminId;
		this.adminEmail = adminEmail;
		this.adminActive = adminActive.name();
		this.createdAt = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.updatedAt = updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.adminRole = adminRole;
	}

	public long getAdminId() {
		return adminId;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public String getAdminActive() {
		return adminActive;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public List<String> getAdminRole() {
		return adminRole;
	}
}
