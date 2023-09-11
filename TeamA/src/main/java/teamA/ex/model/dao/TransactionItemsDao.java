package teamA.ex.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

public interface TransactionItemsDao extends JpaRepository<TransactionItemEntity, Long> {
	TransactionItemEntity save(TransactionItemEntity transactionItemEntity);
	
	@Transactional
	List<TransactionItemEntity> deleteByTransactionId(Long transactiionId);

}
