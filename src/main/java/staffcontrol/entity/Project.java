package staffcontrol.entity;

import lombok.*;
import staffcontrol.constants.Methodology;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Project {
    private Long id;
    private String name;
    private String client;
    private String duration;
    private Methodology methodology;
    private String projectManager;
    private Team team;
}