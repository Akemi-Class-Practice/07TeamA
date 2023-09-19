package teamA.ex.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import teamA.ex.model.entity.LoginAnalyticEntity;

public interface LoginAnalyticDao extends JpaRepository<LoginAnalyticEntity, Long>{

	LoginAnalyticEntity save(LoginAnalyticEntity loginAnalyticEntity);
	
	// ログインの合数クエリ
	@Query(value="SELECT COUNT(*) FROM logins", nativeQuery = true)
	Long countTotal(); 
	
	// 今日のログイン数クエリ
	@Query(value="SELECT COUNT(*) FROM logins WHERE CAST(visit_date_time AS Date) = CURRENT_DATE", nativeQuery = true)
	Long countTotalCountToday();
	
	// 今月のログイン数クエリ
	@Query(value="SELECT COUNT(*) FROM logins WHERE to_char(visit_date_time, 'YYYY-MM') = to_char(CURRENT_DATE, 'YYYY-MM')", nativeQuery = true)
	Long countTotalCountMonth(); 
	
	// 今日の売上クエリ 
	@Query(value="SELECT SUM(amount) FROM transaction_history WHERE CAST(transaction_date AS Date) = CURRENT_DATE", nativeQuery = true)
	Long countTotalSalesToday();
	
	// 今月の売上クエリ
	@Query(value="SELECT SUM(amount) FROM transaction_history WHERE to_char(transaction_date, 'YYYY-MM') = to_char(CURRENT_DATE, 'YYYY-MM')", nativeQuery = true)
	Long countTotalSalesThisMonth();
}
