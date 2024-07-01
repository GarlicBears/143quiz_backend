package garlicbears.quiz.domain.common.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "logs")
public class Log {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "log_seq")
	private long logId;

	//  User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_seq")
	private User user;

	@Column(name = "ip_address", nullable = false)
	private String ipAddress;

	@Column(name = "log_url", nullable = false)
	private String url;

	@Column(name = "log_created_at", nullable = false)
	private LocalDateTime createdAt;

	public Log() {
	}

	public Log(User user, String ipAddress, String url) {
		this.user = user;
		this.ipAddress = ipAddress;
		this.url = url;
		this.createdAt = LocalDateTime.now();
	}

	public Log(long logId, User user, String ipAddress, String url, LocalDateTime createdAt) {
		this.logId = logId;
		this.user = user;
		this.ipAddress = ipAddress;
		this.url = url;
		this.createdAt = createdAt;
	}

	public long getLogId() {
		return logId;
	}

	public void setLogId(long logId) {
		this.logId = logId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
