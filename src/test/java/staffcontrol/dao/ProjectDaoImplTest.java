package staffcontrol.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.jdbc.core.JdbcTemplate;
import staffcontrol.utils.TestDataSourceUtil;
import staffcontrol.constants.Methodology;
import staffcontrol.dao.interfaces.ProjectDAO;
import staffcontrol.dao.interfaces.TeamDAO;
import staffcontrol.dao.spring.jdbc.ProjectDaoSpringJdbcImpl;
import staffcontrol.dao.spring.jdbc.TeamDaoSpringJdbcImpl;
import staffcontrol.entity.Project;
import staffcontrol.entity.Team;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ProjectDaoImplTest {

    private TeamDAO teamDao;
    private ProjectDAO projectDao;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void init() {
        jdbcTemplate = new JdbcTemplate(TestDataSourceUtil.createDataSource(), false);
        teamDao = new TeamDaoSpringJdbcImpl(jdbcTemplate);
        projectDao = new ProjectDaoSpringJdbcImpl(jdbcTemplate, teamDao);
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
        project.setName("XXX projectName");
        project.setClient("XXX client");
        project.setDuration("XXX week");
        project.setMethodology(Methodology.METHODOLOGY_FIRST);
        project.setProjectManager("XXX manager");
        Team createdInDBteam = new Team();
        createdInDBteam.setTitle("XXX team");
        long teamId = teamDao.create(createdInDBteam).getId();
        project.setTeam(teamDao.findById(teamId));
        long projectId = projectDao.create(project).getId();
        Project updateProjectDate = new Project();
        updateProjectDate.setName("XXX_Updated project");
        updateProjectDate.setClient("XXX_Updated client");
        updateProjectDate.setDuration("XXX_Updated week");
        updateProjectDate.setMethodology(Methodology.METHODOLOGY_THIRD);
        updateProjectDate.setProjectManager("XXX_updated manager");
        updateProjectDate.setTeam(project.getTeam());
        projectDao.update(projectId, updateProjectDate);
        Project loadedProject = projectDao.findById(projectId);
        assertEquals(projectDao.findById(projectId).getName(), "XXX_Updated project");
        assertTrue(projectDao.remove(loadedProject.getId()));
        assertTrue(teamDao.remove(teamId));
    }
}
