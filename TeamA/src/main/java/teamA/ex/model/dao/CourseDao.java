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
	 
	 // courseIdに一致する複数のCourseEntityを取得
	 CourseEntity findByCourseId(Long courseId);
	 
	 // CourseEntityのIDに基づいて、該当するBlogEntityを削除するために使用
	 @Transactional
	 List<CourseEntity>deleteByCourseId(Long courseId);

}
