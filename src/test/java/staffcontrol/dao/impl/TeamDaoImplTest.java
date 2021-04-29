package staffcontrol.dao.impl;

import org.junit.Test;
import staffcontrol.ConnectionTestUtil;
import staffcontrol.entity.Team;
import java.sql.SQLException;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TeamDaoImplTest {
    private TeamDaoImpl teamDao = new TeamDaoImpl(new ConnectionTestUtil());


    @Test
    public void createTeam() throws SQLException {
        Team team = new Team();
        Team teamCreatedInDB;
        team.setTitle("new Team name");
        teamCreatedInDB = teamDao.create(team);
        assertNotNull(team.getId());
        assertEquals(team.getTitle(), teamCreatedInDB.getTitle());
        teamDao.remove(teamCreatedInDB.getId());
    }

    @Test
    public void deleteTeam() {
        boolean deleted = true;
        Long id;
        Team team = new Team();
        Team loadedTeam;
        team.setTitle("test deleteTeam title");
        loadedTeam = teamDao.create(team);
        id = loadedTeam.getId();
        assertNotNull(id);
        assertEquals(deleted, teamDao.remove(id));
    }

    @Test
    public void updateTeam() {
        Team team = new Team();
        team.setTitle("test update description");
        team= teamDao.create(team);
        Long idCreated = team.getId();
        assertNotNull(idCreated);
        Team updatedTeam = new Team();
        updatedTeam.setTitle("new, updated description for old feedback");
        teamDao.update(idCreated, updatedTeam);
        assertEquals(teamDao.findById(idCreated).getTitle(), "new, updated description for old feedback");
        teamDao.remove(idCreated);
    }

    @Test
    public void findTeamById() {
        Team findedById;
        Team newTeam = new Team();
        newTeam.setTitle("test findByID description");
        newTeam = teamDao.create(newTeam);
        assertNotNull(newTeam.getId());
        Long id = newTeam.getId();
        findedById = teamDao.findById(id);
        assertEquals(findedById.getTitle(), newTeam.getTitle());
        teamDao.remove(id);
    }

    
    
    
}
