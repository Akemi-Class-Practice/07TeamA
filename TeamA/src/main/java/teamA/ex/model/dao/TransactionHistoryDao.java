package teamA.ex.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface TransactionHistoryDao extends JpaRepository<TransactionHistoryEntity, Long> {
	TransactionHistoryEntity save(TransactionHistoryEntity transactionHistoryEntity);
	@Query(value="SELECT * FROM transaction_history WHERE student_id = ? ORDER BY transaction_id DESC LIMIT 1",
			nativeQuery = true)
	TransactionHistoryEntity getLatestByStudentId(Long studentId);
	
	@Transactional
	List<TransactionHistoryEntity> deleteByTransactionId(Long transactionId);

}
