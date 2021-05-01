package staffcontrol.dao.impl;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import staffcontrol.constants.ExperienceLevel;
import staffcontrol.constants.LanguageLevel;
import staffcontrol.constants.Methodology;
import staffcontrol.entity.Employee;
import staffcontrol.entity.Feedback;
import staffcontrol.entity.Project;
import staffcontrol.entity.Team;
import staffcontrol.util.BasicConnectionPool;

public class EmployeeDaoImplTest {
    private EmployeeDaoImpl employeeDao;
    private TeamDaoImpl teamDao;
    private ProjectDaoImpl projectDAO;
    private FeedbackDaoImpl feedbackDAO;

    @SneakyThrows
    @Before
    public void init() {
        BasicConnectionPool connectionPool = BasicConnectionPool.create();

        feedbackDAO = new FeedbackDaoImpl(connectionPool);
        teamDao = new TeamDaoImpl(connectionPool);
        projectDAO = new ProjectDaoImpl(connectionPool, teamDao);
        employeeDao = new EmployeeDaoImpl(connectionPool);
    }

    @Test
    public void createAndRead() {
        Employee employee = new Employee();

        employee.setFirstName("first_name");
        employee.setLastName("last_name");
        employee.setPhoneNumber("phone_number");
        employee.setEmail("email");
        employee.setSkype("skype");
        employee.setEntryDate((long) (2001 - 12 - 01));
        employee.setExperience("advanced pc user");
        employee.setExperienceLevel(ExperienceLevel.J2);
        employee.setLanguageLevel(LanguageLevel.B1);
        employee.setBirthDay((long) (2000 - 06 - 04));

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
    }

    @Test
    public void updateAndDelete() {
        //TODO this
    }
}


