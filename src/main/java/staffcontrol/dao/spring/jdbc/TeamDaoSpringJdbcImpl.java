package staffcontrol.dao.spring.jdbc;

import lombok.extern.java.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import staffcontrol.dao.interfaces.TeamDAO;
import staffcontrol.entity.Team;

import java.sql.*;
import java.util.List;

@Log
public class TeamDaoSpringJdbcImpl implements TeamDAO {
    private final JdbcTemplate jdbcTemplate;

    public TeamDaoSpringJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Team create(Team team) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO staffcontrol.team (title) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, team.getTitle());
            return ps;
        }, keyHolder);
        long newTeamId = new Long((Integer)keyHolder.getKeyList().get(0).get("id"));
        team.setId(newTeamId);
        return team;
    }

    @Override
    public boolean remove(Long id) {
        boolean result = false;
        try {
            jdbcTemplate.update("DELETE FROM staffcontrol.team WHERE id=?", id);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void update(Long id, Team team) {
        jdbcTemplate.update("UPDATE staffcontrol.team SET title = (?) WHERE id = (?)", team.getTitle(), id);
    }

    public List<Team> findAll() {
        List<Team> feedbacks = jdbcTemplate.query("SELECT * FROM staffcontrol.team", new TeamRowMapper());
        return feedbacks;
    }

    @Override
    public Team findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM staffcontrol.team WHERE id=?", new TeamRowMapper(), id);
    }


    private class TeamRowMapper implements RowMapper<Team> {

        @Override
        public Team mapRow(ResultSet resultSet, int i) throws SQLException {
            Team team = new Team();
            team.setId(resultSet.getLong(1));
            team.setTitle(resultSet.getString("title"));
            return team;
        }
    }
}
