package teamA.ex.model.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ContactEntityPK implements Serializable {

    
    private Long contactId;
    
    private Long studentId;

}
