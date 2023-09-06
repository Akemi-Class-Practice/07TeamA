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
@Table(name = "admins")
public class AdminEntity {
	
	@Id
	
	@Column(name = "admin_id")
	//PKを自動生成する方法を指定します
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long adminId;
	
	@NonNull
	@Column(name = "admin_name")
	private String adminName;
	
	@NonNull
	@Column(name = "admin_email")
	private String adminEmail;
	
	@NonNull
	@Column(name = "register_date")
	private LocalDateTime registerDate;
	
	@NonNull
	@Column(name = "delete_flag")
	private int deleteFlag;
	
	@NonNull
	@Column(name = "admin_icon")
	private String adminIcon;
	
	@NonNull
	@Column(name = "password")
	private String password;
	
	@NonNull
	@Column(name = "salt")
	private String salt;

}
