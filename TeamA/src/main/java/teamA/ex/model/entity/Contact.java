package teamA.ex.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@IdClass(value=ContactEntityPK.class)
public class Contact {
	
	@Id
	@Column(name = "contact_id")
    private Long contactId;
	
	@Id
    @Column(name = "student_id")
    private Long studentId;

    @NonNull
    @Column(name = "student_email")
    private String studentEmail;

    @NonNull
    @Column(name = "contact_title")
    private String contactTitle;

    @NonNull
    @Column(name = "contact_date")
    private LocalDate contactDate;
    
    @NonNull
	@Column(name = "is_done")
	private int isDone;
    
    @NonNull
	@Column(name = "contact_detail")
	private String contactDetail;
}
