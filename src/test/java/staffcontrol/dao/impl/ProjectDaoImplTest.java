package staffcontrol.dao.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import staffcontrol.ConnectionTestUtil;
import staffcontrol.constants.Methodology;
import staffcontrol.dao.interfaces.TeamDAO;
import staffcontrol.entity.Project;
import staffcontrol.entity.Team;

import java.lang.annotation.Target;
import java.sql.Date;

import static org.junit.Assert.*;

public class ProjectDaoImplTest {

    private TeamDaoImpl teamDao;
    private ProjectDaoImpl projectDao;

    @Before
    public void init(){
        ConnectionTestUtil connectionTestUtil = new ConnectionTestUtil();
        teamDao = new TeamDaoImpl(connectionTestUtil);
        projectDao = new ProjectDaoImpl(connectionTestUtil, teamDao);
    }

        @Test
        public void createAndRead(){
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
