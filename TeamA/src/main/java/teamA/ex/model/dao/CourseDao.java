package teamA.ex.model.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import teamA.ex.model.entity.CourseEntity;

public interface CourseDao extends JpaRepository<CourseEntity, Long> {
	
	// コース全てを見るためのメソッド
	
	 List<CourseEntity> findAll();
	 
	 // CourseEntityのオブジェクトを引数として受け取り、そのオブジェクトをデータベースに保存
	 
	 CourseEntity save(CourseEntity courseEntity);
	 
	//titleとregisterDateを検査条件として、blogEntityを取得
	
	 CourseEntity findByCourseNameAndregisterDate(String courseName,LocalDate registerDate);
	 
	//findByCourseId methodを定義し、DBのCourseEntityを検索
		
	 CourseEntity findByCourseId(Long blogId);
		
	//トランザクション管理は複数BD操作をまとめ、一括処理	
		
	 @Transactional
	 List<CourseEntity> deleteByCourseId(Long blogId);
	 
	 

}
