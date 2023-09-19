package teamA.ex.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teamA.ex.model.dao.LoginAnalyticDao;
import teamA.ex.model.entity.LoginAnalyticEntity;

@Service
public class LoginAnalyticService {

	@Autowired 
	private LoginAnalyticDao loginAnalyticDao;
	
	public boolean createAnalytic(LocalDateTime visitDateTime, String ipAddress, Long studentId) {
		loginAnalyticDao.save(new LoginAnalyticEntity(visitDateTime, ipAddress, studentId));
		return true;
	}
	
	public Long countTotal() {
		return loginAnalyticDao.countTotal();
	}
	
	public Long countTotalCountToday() {
		return loginAnalyticDao.countTotalCountToday();
	}
	
	public Long countTotalCountMonth() {
		return loginAnalyticDao.countTotalCountMonth();
	}
	
	public Long countTotalSalesToday() {
		return loginAnalyticDao.countTotalSalesToday();
	}
	
	public Long countTotalSalesThisMonth() {
		return loginAnalyticDao.countTotalSalesThisMonth();
	}
}
