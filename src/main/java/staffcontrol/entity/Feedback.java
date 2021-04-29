package staffcontrol.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Feedback {

    private Long id;
    private String description;
    private Long created;

}
