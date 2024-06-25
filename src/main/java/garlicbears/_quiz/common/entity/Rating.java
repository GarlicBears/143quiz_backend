package garlicbears._quiz.common.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

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
@Table(name = "ratings")
public class Rating {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rating_seq")
	private long ratingId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_seq")
	private User user;

	@Column(name = "rating_value", nullable = false)
	@ColumnDefault("5")
	private double ratingValue;

	@Column(name = "rating_at", nullable = false)
	@ColumnDefault("CURRENT_TIMESTAMP")
	private LocalDateTime ratingDate;

	public Rating() {
	}

	public Rating(User user, double ratingValue) {
		this.user = user;
		this.ratingValue = ratingValue;
		this.ratingDate = LocalDateTime.now();
	}
}
