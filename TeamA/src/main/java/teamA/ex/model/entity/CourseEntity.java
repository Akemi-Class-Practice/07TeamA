package teamA.ex.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

//lombokの宣言
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
//table名を指定します
@Table(name = "courses")
public class CourseEntity {

	@Id
	@Column(name = "course_id")
	//PKを自動生成する方法を指定します
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long courseId;
	
	@NonNull
	@Column(name = "course_name")
	private String courseName;
	
	@NonNull
	@Column(name = "coure_fee")
	private int courseFee;
	
	@Column(name = "course_image")
	private String courseImage;
	
	@NonNull
	@Column(name = "register_date")
	private LocalDate registerDate;
	
	@Column(name = "start_date")
	private LocalDate startDate;
	
	@Column(name = "finish_date")
	private LocalDate finishDate;
	
	@Column(name = "lesson_start_time")
	private LocalTime lessonStartTime;
	
	@Column(name = "lesson_duration")
	private int lessonDuration;
	
	@NonNull
	@Column(name = "admin_id")
	private Long adminId;
	
	@NonNull
	@Column(name = "delete_flag")
	private int deleteFlag;

	public CourseEntity(@NonNull String courseName, @NonNull int courseFee, String courseImage,
			@NonNull LocalDate registerDate, LocalDate startDate, LocalDate finishDate, LocalTime lessonStartTime,
			int lessonDuration, @NonNull Long adminId, @NonNull int deleteFlag) {
		this.courseName = courseName;
		this.courseFee = courseFee;
		this.courseImage = courseImage;
		this.registerDate = registerDate;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.lessonStartTime = lessonStartTime;
		this.lessonDuration = lessonDuration;
		this.adminId = adminId;
		this.deleteFlag = deleteFlag;
	}

	
	
}
