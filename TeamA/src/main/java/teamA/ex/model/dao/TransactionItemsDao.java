package teamA.ex.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import teamA.ex.model.entity.TransactionItemsEntity;

public interface TransactionItemsDao extends JpaRepository<TransactionItemsEntity, Long> {
	TransactionItemsEntity save(TransactionItemsEntity transactionItemsEntity);
	
	@Transactional
	List<TransactionItemsEntity> deleteByTransactionId(Long transactionId);

}
