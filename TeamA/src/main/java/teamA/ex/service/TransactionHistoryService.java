package teamA.ex.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teamA.ex.model.dao.TransactionHistoryDao;
import teamA.ex.model.entity.TransactionHistoryEntity;

@Service
public class TransactionHistoryService {
	@Autowired
	private TransactionHistoryDao transactionHistoryDao;
	//取引履歴の保存処理
	public void createTransactionHistory(Long studentId,int amount) {
		LocalDateTime transactionDate = LocalDateTime.now();
		transactionHistoryDao.save(new TransactionHistoryEntity(studentId,amount,transactionDate));
	}
	
	public TransactionHistoryEntity getTransactoinId(Long studentId) {
		return transactionHistoryDao.getLatestByStudentId(studentId);
	}
	//取引履歴の削除処理
	public List<TransactionHistoryEntity> deleteTransactoinId(Long transactionId) {
		return transactionHistoryDao.deleteByTransactionId(transactionId);
	}
	
	public List<TransactionHistoryEntity> findUserTransactionHistoryByUserId(Long studentId) {
		return transactionHistoryDao.findUserTransactionHistoryByUserId(studentId);
	}
}
