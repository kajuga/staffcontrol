package staffcontrol.entity;

import staffcontrol.constants.Methodology;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;;

@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private Long id;
    private String name;
    private String client;
    private String duration;
    private Methodology methodology;
    private String projectManager;
    private Team team;
}