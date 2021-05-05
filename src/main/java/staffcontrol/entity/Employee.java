package staffcontrol.entity;

import lombok.*;
import staffcontrol.constants.ExperienceLevel;
import staffcontrol.constants.LanguageLevel;

import java.sql.Date;
import java.time.LocalDate;


@Data
@Setter
@Getter
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String skype;
    private Date entryDate;
    private String experience;
    private ExperienceLevel experienceLevel;
    private LanguageLevel languageLevel;
    private Date birthDay;
    private Project project;
    private Feedback feedback;
}


