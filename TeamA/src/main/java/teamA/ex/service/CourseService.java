package teamA.ex.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;

import teamA.ex.model.dao.CourseDao;
import teamA.ex.model.entity.CourseEntity;

public class CourseService {
	
	@Autowired 
	private CourseDao courseDao;

	public boolean createCourse(String courseName, int courseFee, String courseImage, LocalDate registerDate, LocalDate startDate, LocalDate finishDate, LocalTime lessonStartTime, int lessonDuration, Long adminId, int deleteFlag) {
		courseDao.save(new CourseEntity(courseName, courseFee, courseImage, registerDate, startDate, finishDate, lessonStartTime, lessonDuration, adminId, deleteFlag));
		return true;
	}
}
