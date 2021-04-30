package staffcontrol.dao.impl;

import org.junit.Before;
import org.junit.Test;
import staffcontrol.ConnectionTestUtil;
import staffcontrol.constants.ExperienceLevel;
import staffcontrol.constants.LanguageLevel;
import staffcontrol.constants.Methodology;
import staffcontrol.entity.Employee;
import staffcontrol.entity.Feedback;
import staffcontrol.entity.Project;
import staffcontrol.entity.Team;

public class EmployeeDaoImplTest {
    private EmployeeDaoImpl employeeDao;
    private TeamDaoImpl teamDao;
    private ProjectDaoImpl projectDAO;
    private FeedbackDaoImpl feedbackDAO;


    @Before
    public void init(){
        ConnectionTestUtil connectionTestUtil = new ConnectionTestUtil();
        feedbackDAO = new FeedbackDaoImpl(connectionTestUtil);
        teamDao = new TeamDaoImpl(connectionTestUtil);
        projectDAO = new ProjectDaoImpl(connectionTestUtil, teamDao);
        employeeDao = new EmployeeDaoImpl(connectionTestUtil);
    }

    @Test
    public void createAndRead(){
        Employee employee = new Employee();

        employee.setFirstName("first_name");
        employee.setLastName("last_name");
        employee.setPhoneNumber("phone_number");
        employee.setEmail("email");
        employee.setSkype("skype");
        employee.setEntryDate((long) (2001-12-01));
        employee.setExperience("advanced pc user");
        employee.setExperienceLevel(ExperienceLevel.J2);
        employee.setLanguageLevel(LanguageLevel.B1);
        employee.setBirthDay((long) (2000-06-04));

        Project project = new Project();
        project.setName("Kremlin vote counting system");
        project.setClient("Men in black masks");
        project.setDuration("Three week");
        project.setMethodology(Methodology.METHODOLOGY_SECOND);
        project.setProjectManager("Surkov V.J.");
        Team createdInDBteam = new Team();
        createdInDBteam.setTitle("Inglourious Basterds");
        long teamId = teamDao.create(createdInDBteam).getId();
        project.setTeam(teamDao.findById(teamId));
        projectDAO.create(project);

        Feedback feedback = new Feedback();
        feedback.setId(null);
        feedback.setDescription("new feedback");
        feedbackDAO.create(feedback);
        employee.setProject(project);
        employee.setFeedback(feedback);
        long createdInDbId = employeeDao.create(employee).getId();

        //TODO created, but findById not worket yet
    }

    @Test
    public void updateAndDelete() {
    //TODO this
    }
}


