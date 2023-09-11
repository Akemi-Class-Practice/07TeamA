package teamA.ex.model.entity;

import java.time.LocalDateTime;

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
@Table(name="transaction_history")
public class TransactionHistoryEntity {
	
	@Id
	@Column(name="transaction_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long transactionId;
	
	@Column(name="student_id")
	private Long studentId;
	
	@Column(name="amount")
	private int amount;
	
	@Column(name="transaction_date")
	private LocalDateTime transactionDate;

	public TransactionHistoryEntity(Long studentId, int amount, LocalDateTime transactionDate) {
		this.studentId = studentId;
		this.amount = amount;
		this.transactionDate = transactionDate;
	}
	
}