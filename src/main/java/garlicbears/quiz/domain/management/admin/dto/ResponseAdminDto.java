package garlicbears.quiz.domain.management.admin.dto;

import java.time.LocalDateTime;

import garlicbears.quiz.domain.common.entity.Active;

public class ResponseAdminDto {
	private long adminId;
	private String adminEmail;
	private String adminActive;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public ResponseAdminDto(long adminId, String adminEmail, Active adminActive,
		LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.adminId = adminId;
		this.adminEmail = adminEmail;
		this.adminActive = adminActive.name();
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
}
