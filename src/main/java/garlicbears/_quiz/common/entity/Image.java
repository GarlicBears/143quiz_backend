package garlicbears._quiz.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "images")
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_seq")
	private Long imageId;

	@Column(name = "image_name", nullable = false, length = 200)
	private String imageName;

	@Lob
	@Column(name = "image_data", nullable = false)
	private byte[] imageData;
}
