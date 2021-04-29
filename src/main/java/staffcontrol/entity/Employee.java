package staffcontrol.entity;

import staffcontrol.constants.ExperienceLevel;
import staffcontrol.constants.LanguageLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String skype;
    private String entryDate;
    private String experience;
    private ExperienceLevel experienceLevel;
    private LanguageLevel languageLevel;
    private String birthDay;
    private Project project;
    private Feedback feedback;
}


