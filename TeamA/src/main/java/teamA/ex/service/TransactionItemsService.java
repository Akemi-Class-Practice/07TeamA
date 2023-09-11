package teamA.ex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import teamA.ex.model.dao.TransactionItemsDao;
import teamA.ex.model.entity.TransactionItemsEntity;

public class TransactionItemsService {
	
	@Autowired
	private TransactionItemsDao transactionItemsDao;
	
	// 保存処理
	public void createTransactionHistory(Long transactionId,Long courseId) {
		transactionItemsDao.save(new TransactionItemsEntity(transactionId,courseId));
	}
	
	public List<TransactionItemsEntity> deleteTransactionId(Long transactionId) {
		return transactionItemsDao.deleteByTransactionId(transactionId);
	}

}
