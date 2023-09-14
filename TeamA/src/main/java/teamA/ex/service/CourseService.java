package teamA.ex.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teamA.ex.model.dao.CourseDao;
import teamA.ex.model.entity.CourseEntity;

@Service
public class CourseService {
	
	// CourseDaoインターフェースを使用可能とする
	@Autowired 
	private CourseDao courseDao;
	
	// 以下はコースを追加するためのメソッド
	public boolean createCourse(String courseName, int courseFee, String courseImage, LocalDate registerDate, LocalDate startDate, LocalDate finishDate, LocalTime lessonStartTime, int lessonDuration, Long adminId, int deleteFlag, String courseInfo) {
		courseDao.save(new CourseEntity(courseName, courseFee, courseImage, registerDate, startDate, finishDate, lessonStartTime, lessonDuration, adminId, deleteFlag, courseInfo));
		return true;
	}
	
	public List<CourseEntity> findAll(){
		return courseDao.findAll();
	}
	
	public List<CourseEntity> findCoursesForUser(){
		return courseDao.findCoursesForUser();
	}
	
	// CourseIdに基づいて、CourseDaoから該当するCourseEntityを取得して返す。
	public CourseEntity getCourse(Long courseId) {
		if (courseId == null) {
			return null;
		} else {
			return courseDao.findByCourseId(courseId);
		}
	}
	
//	 講座のタイトル、登録日、詳細などの情報を受け取り、指定されたCourseIdに対応する講座を更新
	public boolean editCoursePost(String courseName, int courseFee, String courseImage, 
			LocalDate startDate, LocalDate finishDate, 
			LocalTime lessonStartTime,int lessonDuration, 
			String courseInfo, Long courseId) {
		
		CourseEntity courseList = courseDao.findByCourseId(courseId);
		if (courseList == null) {
			return false;
		}else {
			courseList.setCourseName(courseName);
			courseList.setCourseFee(courseFee);
			courseList.setCourseImage(courseImage);
			courseList.setStartDate(startDate);
			courseList.setFinishDate(finishDate);
			courseList.setLessonStartTime(lessonStartTime);
			courseList.setLessonDuration(lessonDuration);
			courseList.setCourseInfo(courseInfo);
			courseDao.save(courseList);
			return true;
		}
			
	}

	// メソッド名と引数を定義。コースID,ファイル名、管理者IDを引数として受け取る
	public boolean editCourseImage(Long courseId, String courseImage, Long adminId) {
		CourseEntity courseList = courseDao.findByCourseId(courseId);
		
		if(courseImage == null || courseList.getCourseImage().equals(courseImage)) {
			return false;
		}else {
			courseList.setCourseId(courseId);
			courseList.setCourseImage(courseImage);
			courseList.setAdminId(adminId);
			courseDao.save(courseList);
			return true;
		}
		
	}
	
	// 削除処理を行うためのメソッド
	public boolean deleteCourse(Long courseId) {
		CourseEntity courseList = courseDao.findByCourseId(courseId);
		if(courseList == null) {
			return false;
		}else {
			courseList.setDeleteFlag(1);
			courseDao.save(courseList);			
			return true;
		}
	}
	
}










