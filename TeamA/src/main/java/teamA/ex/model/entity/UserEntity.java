package teamA.ex.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name = "students")
public class UserEntity {

	public UserEntity(String name2, String email2, String password2, LocalDateTime registerDate2) {
		// TODO Auto-generated constructor stub
	}

	@Id
	
	@Column(name = "student_id")
	//PKを自動生成する方法を指定します
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Long studentId;
	
	@NonNull
	@Column(name = "student_name")
	private String name;
	
	@NonNull
	@Column(name = "student_email")
	private String email;
	
	@NonNull
	@Column(name = "register_date")
	private LocalDate registerDate;
	
	@NonNull
	@Column(name = "delete_flag")
	private int deleteFlag;
	
	@NonNull
	@Column(name = "student_icon")
	private String icon;
	
	@NonNull
	@Column(name = "password")
	private String password;
	
	@NonNull
	@Column(name = "salt")
	private String salt;
}
