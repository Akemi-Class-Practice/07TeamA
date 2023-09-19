package teamA.ex.model.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import teamA.ex.model.entity.CourseEntity;
import teamA.ex.model.entity.TransactionHistoryEntity;


public interface CourseDao extends JpaRepository<CourseEntity, Long> {
	
	// コース全てを見るためのメソッド	
	List<CourseEntity> findAll();
	
	// 生徒の見える講座リストメソッド
	@Query(value="SELECT * FROM courses WHERE delete_flag = 0 AND start_date >= CURRENT_DATE", nativeQuery = true) 
	List<CourseEntity> findCoursesForUser();
	
	// CourseEntityのオブジェクトを引数として受け取り、そのオブジェクトをデータベースに保存
	CourseEntity save(CourseEntity courseEntity);
		 
	//courseNameとregisterDateを検査条件として、CourseEntityを取得
	CourseEntity findByCourseNameAndRegisterDate(String courseName,LocalDate registerDate);
		 
	//findByCourseId methodを定義し、DBのCourseEntityを検索
	CourseEntity findByCourseId(Long courseId);
			
	//トランザクション管理は複数BD操作をまとめ、一括処理	
	@Transactional
	List<CourseEntity> deleteByCourseId(Long courseId);
	
	//講座の名前で検索する処理
	@Query(value="SELECT * FROM courses WHERE course_name ILIKE '%' || ?1 || '%'", nativeQuery = true)
	List<CourseEntity> findByCourseName(String searchName);

}
