package staffcontrol.dao.impl;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import staffcontrol.constants.ExperienceLevel;
import staffcontrol.constants.LanguageLevel;
import staffcontrol.constants.Methodology;
import staffcontrol.entity.Employee;
import staffcontrol.entity.Feedback;
import staffcontrol.entity.Project;
import staffcontrol.entity.Team;
import staffcontrol.util.BasicConnectionPool;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class EmployeeDaoImplTest {
    private  static BasicConnectionPool connectionPool;
    private EmployeeDaoImpl employeeDao;
    private TeamDaoImpl teamDao;
    private ProjectDaoImpl projectDAO;
    private FeedbackDaoImpl feedbackDAO;

    @BeforeClass
    public static void initConnectionPool() {
        connectionPool = BasicConnectionPool.create();
    }

    @Before
    public void init() {
        feedbackDAO = new FeedbackDaoImpl(connectionPool);
        teamDao = new TeamDaoImpl(connectionPool);
        projectDAO = new ProjectDaoImpl(connectionPool, teamDao);
        employeeDao = new EmployeeDaoImpl(connectionPool, projectDAO, feedbackDAO);
    }

    @AfterClass
    public static void destroy() throws SQLException {
        connectionPool.shutdown();
    }


    @Test
    public void createEmployee() throws SQLException {
        Employee employee = new Employee();
        employee.setFirstName("German");
        employee.setLastName("Ivanov");
        employee.setPhoneNumber("+79195458746");
        employee.setEmail("xxx@gmail.com");
        employee.setSkype("@XXX");
        employee.setEntryDate(new Date(2010, 2,1));
        employee.setExperience("advanced PC user");
        employee.setExperienceLevel(ExperienceLevel.J2);
        employee.setLanguageLevel(LanguageLevel.B1);
        employee.setBirthDay(new Date(2000, 11, 18));
        Team team = new Team();
        team.setTitle("test team title from "+ employee.getLastName() + " " + employee.getFirstName());
        team = teamDao.create(team);
        Project project = new Project();
        project.setName("test project from "+ employee.getLastName() + " " + employee.getFirstName());
        project.setClient("cool TEST client");
        project.setDuration("three weeks");
        project.setMethodology(Methodology.METHODOLOGY_SECOND);
        project.setProjectManager("Artur Pirojkov");
        project.setTeam(team);
        project = projectDAO.create(project);
        employee.setProject(project);
        Feedback feedback = new Feedback();
        feedback.setDescription("test feedback date from " + employee.getLastName() + " " + employee.getFirstName());
        long feedbackId = feedbackDAO.create(feedback).getId();
        employee.setFeedback(feedback);
        Employee created = employeeDao.create(employee);
        assertEquals(created.getFirstName() + created.getLastName(), employee.getFirstName() + employee.getLastName());
        EmployeeEraserUtil(team, project, feedbackId, created);
    }

    @Test
    public void findById() {
        Employee employee = new Employee();
        employee.setFirstName("German");
        employee.setLastName("Ivanov");
        employee.setPhoneNumber("+79195458746");
        employee.setEmail("xxx@gmail.com");
        employee.setSkype("@XXX");
        employee.setEntryDate(new Date(1986, 4, 21));
        employee.setExperience("advanced PC user");
        employee.setExperienceLevel(ExperienceLevel.J2);
        employee.setLanguageLevel(LanguageLevel.B1);
        employee.setBirthDay(new Date(1983, 2, 3));
        Team team = new Team();
        team.setTitle("test team title from "+ employee.getLastName() + " " + employee.getFirstName());
        team = teamDao.create(team);
        Project project = new Project();
        project.setName("test project from "+ employee.getLastName() + " " + employee.getFirstName());
        project.setClient("cool TEST client");
        project.setDuration("three weeks");
        project.setMethodology(Methodology.METHODOLOGY_SECOND);
        project.setProjectManager("Artur Pirojkov");
        project.setTeam(team);
        project = projectDAO.create(project);
        employee.setProject(project);
        Feedback feedback = new Feedback();
        feedback.setDescription("test feedback date from " + employee.getLastName() + " " + employee.getFirstName());
        long feedbackId = feedbackDAO.create(feedback).getId();
        employee.setFeedback(feedback);
        Employee createdInDB = employeeDao.create(employee);
        long createdEmployeeId = createdInDB.getId();
        Employee loadedEmployee = employeeDao.findById(createdEmployeeId);
        assertEquals(loadedEmployee.getFirstName(), "German");
        assertEquals(loadedEmployee.getLastName(), "Ivanov");
        assertEquals(loadedEmployee.getSkype(), "@XXX");
        EmployeeEraserUtil(team, project, feedbackId, loadedEmployee);
    }


    @Test
    public void findAll() {
        List<Employee> added = new ArrayList<>();
        List<Employee> finded;

        Employee employee_ONE = new Employee();
        employee_ONE.setFirstName("German");
        employee_ONE.setLastName("Ivanov");
        employee_ONE.setPhoneNumber("+79195458746");
        employee_ONE.setEmail("xxx@gmail.com");
        employee_ONE.setSkype("@XXX");
        employee_ONE.setEntryDate(new Date(1955, 1, 12));
        employee_ONE.setExperience("advanced PC user");
        employee_ONE.setExperienceLevel(ExperienceLevel.J2);
        employee_ONE.setLanguageLevel(LanguageLevel.B1);
        employee_ONE.setBirthDay(new Date(1933, 7, 11));
        Team team_ONE = new Team();
        team_ONE.setTitle("test team title from "+ employee_ONE.getLastName() + " " + employee_ONE.getFirstName());
        team_ONE = teamDao.create(team_ONE);
        Project project_ONE = new Project();
        project_ONE.setName("test project from "+ employee_ONE.getLastName() + " " + employee_ONE.getFirstName());
        project_ONE.setClient("cool TEST client");
        project_ONE.setDuration("three weeks");
        project_ONE.setMethodology(Methodology.METHODOLOGY_SECOND);
        project_ONE.setProjectManager("Artur Pirojkov");
        project_ONE.setTeam(team_ONE);
        project_ONE = projectDAO.create(project_ONE);
        employee_ONE.setProject(project_ONE);
        Feedback feedback_ONE = new Feedback();
        feedback_ONE.setDescription("test feedback date from " + employee_ONE.getLastName() + " " + employee_ONE.getFirstName());
        long feedbackONEId = feedbackDAO.create(feedback_ONE).getId();
        employee_ONE.setFeedback(feedback_ONE);
        employee_ONE = employeeDao.create(employee_ONE);


        Employee employee_TWO = new Employee();
        employee_TWO.setFirstName("Russlan");
        employee_TWO.setLastName("Sergeev");
        employee_TWO.setPhoneNumber("+88888888888888");
        employee_TWO.setEmail("yyy@mail.ru");
        employee_TWO.setSkype("@YYY");
        employee_TWO.setEntryDate(new Date(1911, 4, 14));
        employee_TWO.setExperience("advanced Mac user");
        employee_TWO.setExperienceLevel(ExperienceLevel.J1);
        employee_TWO.setLanguageLevel(LanguageLevel.B2);
        employee_TWO.setBirthDay(new Date(1922, 5, 27));
        Team team_TWO = new Team();
        team_TWO.setTitle("team title "+ employee_TWO.getLastName() + " " + employee_TWO.getFirstName());
        team_TWO = teamDao.create(team_TWO);
        Project project_TWO = new Project();
        project_TWO.setName("Project from "+ employee_TWO.getLastName() + " " + employee_TWO.getFirstName());
        project_TWO.setClient("cool TEST_TWO client");
        project_TWO.setDuration("three weeks_TWO");
        project_TWO.setMethodology(Methodology.METHODOLOGY_FIRST);
        project_TWO.setProjectManager("John Rambo");
        project_TWO.setTeam(team_TWO);
        project_TWO = projectDAO.create(project_TWO);
        employee_TWO.setProject(project_TWO);
        Feedback feedback_TWO = new Feedback();
        feedback_TWO.setDescription("test feedback date from " + employee_TWO.getLastName() + " " + employee_TWO.getFirstName());
        long feedback_TWOId = feedbackDAO.create(feedback_TWO).getId();
        employee_TWO.setFeedback(feedback_TWO);

        employee_TWO = employeeDao.create(employee_TWO);
        added.add(employee_ONE);
        added.add(employee_TWO);
        finded = employeeDao.findAll();
        for (Employee employee: finded) {
            System.out.println(employee);
        }
        EmployeeEraserUtil(team_ONE, project_ONE, feedbackONEId, employee_ONE);
        EmployeeEraserUtil(team_TWO, project_TWO, feedback_TWOId, employee_TWO);
    }


    @Test
    public void update() {
            Employee employee = new Employee();
            employee.setFirstName("German");
            employee.setLastName("Ivanov");
            employee.setPhoneNumber("+79195458746");
            employee.setEmail("xxx@gmail.com");
            employee.setSkype("@XXX");
            employee.setEntryDate(new Date(1944, 1, 13));
            employee.setExperience("advanced PC user");
            employee.setExperienceLevel(ExperienceLevel.J2);
            employee.setLanguageLevel(LanguageLevel.B1);
            employee.setBirthDay(new Date(1912, 9, 14));
            Team team = new Team();
            team.setTitle("test team title from "+ employee.getLastName() + " " + employee.getFirstName());
            team = teamDao.create(team);
            long teamId = team.getId();
            Project project = new Project();
            project.setName("test project from "+ employee.getLastName() + " " + employee.getFirstName());
            project.setClient("cool TEST client");
            project.setDuration("three weeks");
            project.setMethodology(Methodology.METHODOLOGY_SECOND);
            project.setProjectManager("Artur Pirojkov");
            project.setTeam(team);
            project = projectDAO.create(project);
            long projectId = project.getId();
            employee.setProject(project);
            Feedback feedback = new Feedback();
            feedback.setDescription("test feedback date from " + employee.getLastName() + " " + employee.getFirstName());
            long feedbackId = feedbackDAO.create(feedback).getId();
            employee.setFeedback(feedbackDAO.findById(feedbackId));
            long employeId = employeeDao.create(employee).getId();
            //create employee with new date
            Employee employeeWithNewDate = employeeDao.findById(employeId);
            employeeWithNewDate.setFirstName("GermanNEW");
            employeeWithNewDate.setLastName("IvanovNEW");
            employeeWithNewDate.setPhoneNumber("+666666666");
            employeeWithNewDate.setEmail("xxx@gmail.com");
            employeeWithNewDate.setSkype("@XXX_NEW");
            employeeWithNewDate.setEntryDate(new Date(1966, 11, 22));
            employeeWithNewDate.setExperience("advanced PC user_NEW");
            employeeWithNewDate.setExperienceLevel(ExperienceLevel.J1);
            employeeWithNewDate.setLanguageLevel(LanguageLevel.B2);
            employeeWithNewDate.setBirthDay(new Date(1946, 7, 19));
            Team teamNew = teamDao.findById(teamId);
            teamNew.setTitle("test team title from "+ employeeWithNewDate.getLastName() + " " + employeeWithNewDate.getFirstName());
//            teamNew = teamDao.create(teamNew);
            Project projectNew = projectDAO.findById(projectId);
            projectNew.setName("test project from "+ employeeWithNewDate.getLastName() + " " + employeeWithNewDate.getFirstName());
            projectNew.setClient("cool TEST client");
            projectNew.setDuration("three weeks");
            projectNew.setMethodology(Methodology.METHODOLOGY_SECOND);
            projectNew.setProjectManager("Artur Pirojkov_NEW");
            projectNew.setTeam(teamNew);
            projectDAO.update(projectId, projectNew);
//            projectNew = projectDAO.create(projectNew);
            employeeWithNewDate.setProject(projectNew);
            Feedback feedbackNew = feedbackDAO.findById(feedbackId);
            feedbackNew.setDescription("test feedback date from " + employeeWithNewDate.getLastName() + " " + employeeWithNewDate.getFirstName());
//            feedbackDAO.create(feedbackNew);
            employeeWithNewDate.setFeedback(feedbackNew);
            employeeDao.update(employeId, employeeWithNewDate);
            assertEquals(employeeDao.findById(employeId).getFirstName(), "GermanNEW");
            assertEquals(employeeDao.findById(employeId).getLastName(), "IvanovNEW");
            assertEquals(employeeDao.findById(employeId).getPhoneNumber(), "+666666666");
            EmployeeEraserUtil(team, project, feedbackId, employeeWithNewDate);


        }


    @Test
    public void deleteEmployee() {
        Long id;
        Employee employee = new Employee();
        employee.setFirstName("German");
        employee.setLastName("Ivanov");
        employee.setPhoneNumber("+79195458746");
        employee.setEmail("xxx@gmail.com");
        employee.setSkype("@XXX");
        employee.setEntryDate(new Date(1901, 2, 11));
        employee.setExperience("advanced PC user");
        employee.setExperienceLevel(ExperienceLevel.J2);
        employee.setLanguageLevel(LanguageLevel.B1);
        employee.setBirthDay(new Date(1986, 6, 16));
        Team team = new Team();
        team.setTitle("test team title from "+ employee.getLastName() + " " + employee.getFirstName());
        team = teamDao.create(team);
        Project project = new Project();
        project.setName("test project from "+ employee.getLastName() + " " + employee.getFirstName());
        project.setClient("cool TEST client");
        project.setDuration("three weeks");
        project.setMethodology(Methodology.METHODOLOGY_SECOND);
        project.setProjectManager("Artur Pirojkov");
        project.setTeam(team);
        project = projectDAO.create(project);
        employee.setProject(project);
        Feedback feedback = new Feedback();
        feedback.setDescription("test feedback date from " + employee.getLastName() + " " + employee.getFirstName());
        long feedbackId = feedbackDAO.create(feedback).getId();
        employee.setFeedback(feedbackDAO.findById(feedbackId));
        id = employeeDao.create(employee).getId();
        assertNotNull(id);
        boolean deleted;
        deleted = employeeDao.remove(id);
        assertTrue(deleted);
        assertNull(employeeDao.findById(id).getFirstName());
        assertNull(employeeDao.findById(id).getLastName());
        assertNull(employeeDao.findById(id).getEmail());
        assertNull(employeeDao.findById(id).getEntryDate());
        assertNull(employeeDao.findById(id).getExperience());
        assertNull(employeeDao.findById(id).getExperienceLevel());
        EmployeeEraserUtil(team, project, feedbackId, employee);
    }


    private void EmployeeEraserUtil(Team team, Project project, long feedbackId, Employee created) {
        employeeDao.remove(created.getId());
        projectDAO.remove(project.getId());
        teamDao.remove(team.getId());
        feedbackDAO.remove(feedbackId);
    }
}


