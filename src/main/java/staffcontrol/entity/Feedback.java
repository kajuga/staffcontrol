package staffcontrol.entity;

import lombok.*;

import java.sql.Date;

@Data
public class Feedback {
    private Long id;
    private String description;
    private Date created;
}
