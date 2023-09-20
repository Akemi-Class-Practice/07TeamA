package teamA.ex.model.entity;


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

@Entity
//table名を指定します
@Table(name = "logins")
public class LoginAnalyticEntity {

	public LoginAnalyticEntity(LocalDateTime visitDateTime, String ipAddress, Long studentId) {
		this.visitDateTime = visitDateTime;
		this.ipAddress = ipAddress;
		this.studentId = studentId;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long loginId;
	
	@Column(name = "student_id")
	private Long studentId;
	
	@Column(name = "visit_date_time")
	private LocalDateTime visitDateTime;
	
	@Column(name = "ip_address")
	private String ipAddress;
}
