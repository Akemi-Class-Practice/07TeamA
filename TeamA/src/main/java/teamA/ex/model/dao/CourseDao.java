package teamA.ex.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import teamA.ex.model.entity.CourseEntity;

public interface CourseDao extends JpaRepository<CourseEntity, Long> {
	
	// コース全てを見るためのメソッド
	 List<CourseEntity> findAll();
	 
	 // CourseEntityのオブジェクトを引数として受け取り、そのオブジェクトをデータベースに保存
	 CourseEntity save(CourseEntity courseEntity);
	 
	 CourseEntity findByCourseId(Long courseId);
}
