package teamA.ex.model.entity;

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
@Table(name = "contacts")
public class ContactEntity {
	
	@Id
	
	@Column(name = "contact_id")
	//PKを自動生成する方法を指定します
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long contactId;
	
	@NonNull
	@Column(name = "contact_title")
	private String contactTitle;
	
	@NonNull
	@Column(name = "contact_detail")
	private String contactDetail;
	
	@NonNull
	@Column(name = "user_id")
	private Long userId;
	
	@NonNull
	@Column(name = "is_done")
	private int isDone;

}
