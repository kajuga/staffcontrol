package staffcontrol.entity;

import lombok.*;
import staffcontrol.constants.ExperienceLevel;
import staffcontrol.constants.LanguageLevel;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String skype;
    private Long entryDate;
    private String experience;
    private ExperienceLevel experienceLevel;
    private LanguageLevel languageLevel;
    private Long birthDay;
    private Project project;
    private Feedback feedback;
}


