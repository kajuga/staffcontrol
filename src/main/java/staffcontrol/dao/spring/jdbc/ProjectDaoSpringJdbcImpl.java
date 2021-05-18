package staffcontrol.dao.spring.jdbc;

import lombok.extern.java.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import staffcontrol.constants.Methodology;
import staffcontrol.dao.interfaces.ProjectDAO;
import staffcontrol.dao.interfaces.TeamDAO;
import staffcontrol.entity.Project;
import staffcontrol.entity.Team;

import java.sql.*;
import java.util.List;

@Log
public class ProjectDaoSpringJdbcImpl implements ProjectDAO {

    private final JdbcTemplate jdbcTemplate;
    private final TeamDAO teamDAO;

    public ProjectDaoSpringJdbcImpl(JdbcTemplate jdbcTemplate, TeamDAO teamDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.teamDAO = teamDAO;
    }

    @Override
    public Project create(Project project) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement prepStat = con.prepareStatement(
                    "INSERT INTO staffcontrol.project (name, client, duration, methodology, project_manager, team_id) VALUES (?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            prepStat.setString(1, project.getName());
            prepStat.setString(2, project.getClient());
            prepStat.setString(3, project.getDuration());
            prepStat.setString(4, String.valueOf(project.getMethodology()));
            prepStat.setString(5, project.getProjectManager());
            prepStat.setLong(6, project.getTeam().getId());
            return prepStat;
        }, keyHolder);
        long newProjectId = new Long((Integer) keyHolder.getKeyList().get(0).get("id"));
        project.setId(newProjectId);
        return project;
    }

    @Override
    public boolean remove(Long id) {
        boolean result = false;
        try {
            jdbcTemplate.update("DELETE FROM staffcontrol.project WHERE id=?", id);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void update(Long id, Project project) {
        jdbcTemplate.update("UPDATE staffcontrol.project SET name = (?), " +
                        "client=(?), duration=(?), methodology=(?), project_manager=(?), team_id=(?) WHERE id = (?)",
                project.getName(), project.getClient(), project.getDuration(), String.valueOf(project.getMethodology()),
                project.getProjectManager(), project.getTeam().getId(), id);
    }

    @Override
    public Project findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM staffcontrol.project WHERE id=?", new ProjectRowMapper(), id);
    }


    @Override
    public List<Project> findAll() {
        List<Project> projects = jdbcTemplate.query("SELECT * FROM staffcontrol.project", new ProjectRowMapper());
        return projects;
    }


    private class ProjectRowMapper implements RowMapper<Project> {

        @Override
        public Project mapRow(ResultSet resultSet, int i) throws SQLException {
            Project project = new Project();
            project.setId(resultSet.getLong(1));
            project.setName(resultSet.getString(2));
            project.setClient(resultSet.getString(3));
            project.setDuration(resultSet.getString(4));
            String methodology = resultSet.getString("methodology");
            project.setMethodology(methodology != null ? Methodology.fromKey(methodology) : null);
            project.setProjectManager(resultSet.getString(6));
            Team team = teamDAO.findById(resultSet.getLong(7));
            project.setTeam(team);
            return project;
        }
    }

}

