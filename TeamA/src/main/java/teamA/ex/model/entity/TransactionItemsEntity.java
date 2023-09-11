package teamA.ex.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="transaction_item")
public class TransactionItemsEntity {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="transaction_id")
	private Long transactionId;
	
	@Column(name="course_id")
	private Long courseId;

	public TransactionItemsEntity(Long transactionId, Long courseId) {
		this.transactionId = transactionId;
		this.courseId = courseId;
	}
	
	
}
