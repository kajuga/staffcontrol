package staffcontrol.dao;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import staffcontrol.constants.Methodology;
import staffcontrol.dao.jdbc.ProjectDaoImpl;
import staffcontrol.dao.jdbc.TeamDaoImpl;
import staffcontrol.entity.Project;
import staffcontrol.entity.Team;
import staffcontrol.util.BasicConnectionPool;

import java.sql.SQLException;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ProjectDaoImplTest {

    private TeamDaoImpl teamDao;
    private ProjectDaoImpl projectDao;
    private static BasicConnectionPool basicConnectionPool;

    @BeforeClass
    public static void initConnectionPool() {
        basicConnectionPool = BasicConnectionPool.create();
    }

    @Before
    public void init() {
        teamDao = new TeamDaoImpl(basicConnectionPool);
        projectDao = new ProjectDaoImpl(basicConnectionPool, teamDao);
    }

    @AfterClass
    public static void destroy() throws SQLException {
        basicConnectionPool.shutdown();
    }

    @Test
    public void createAndRead() {
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
        Project loadedFromDBProject = projectDao.create(project);
        assertEquals(loadedFromDBProject.getName(), "Kremlin vote counting system");
        assertEquals(loadedFromDBProject.getTeam().getTitle(), "Inglourious Basterds");
        assertEquals(loadedFromDBProject.getProjectManager(), "Surkov V.J.");
        assertEquals(loadedFromDBProject.getMethodology(), Methodology.METHODOLOGY_SECOND);
        projectDao.remove(loadedFromDBProject.getId());
        teamDao.remove(teamId);
    }

    @Test
    public void updateAndDelete() {
        Project project = new Project();
        project.setName("XXX project");
        project.setClient("XXX client");
        project.setDuration("XXX week");
        project.setMethodology(Methodology.METHODOLOGY_FIRST);
        project.setProjectManager("XXX");
        Team createdInDBteam = new Team();
        createdInDBteam.setTitle("XXX team");
        long teamId = teamDao.create(createdInDBteam).getId();
        project.setTeam(teamDao.findById(teamId));
        long projectId = projectDao.create(project).getId();
        Project updateDate = new Project();
        updateDate.setName("XXX_Updated project");
        updateDate.setClient("XXX_Updatet client");
        updateDate.setDuration("XXX_Updated week");
        updateDate.setMethodology(Methodology.METHODOLOGY_THIRD);
        updateDate.setProjectManager("XXX_updated");
        updateDate.setTeam(project.getTeam());
        projectDao.update(projectId, updateDate);
        Project loadedProject = projectDao.findById(projectId);
        assertEquals(projectDao.findById(projectId).getName(), "XXX_updated");
        assertTrue(projectDao.remove(loadedProject.getId()));
        assertTrue(teamDao.remove(teamId));
    }
}
