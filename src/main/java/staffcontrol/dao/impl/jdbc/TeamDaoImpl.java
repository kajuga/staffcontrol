package staffcontrol.dao.impl.jdbc;

import lombok.extern.java.Log;
import staffcontrol.dao.interfaces.TeamDAO;
import staffcontrol.entity.Team;
import staffcontrol.util.BasicConnectionPool;

import java.sql.*;

@Log
public class TeamDaoImpl implements TeamDAO {
    private final BasicConnectionPool connectionPool;

    public TeamDaoImpl(BasicConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Team create(Team team) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement prepStat = connection
                    .prepareStatement("INSERT INTO staffcontrol.team (title) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            prepStat.setString(1, team.getTitle());
            prepStat.executeUpdate();
            int idTeam = -1;
            ResultSet generatedKeys = prepStat.getGeneratedKeys();
            if (generatedKeys.next()) {
                idTeam = generatedKeys.getInt(1);
                team.setId(idTeam);
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        log.fine("Team " + team.getId() + " successfull created");
        return team;
    }

    @Override
    public boolean remove(Long id) {
        boolean result = false;
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement prepStat = connection
                    .prepareStatement("DELETE FROM staffcontrol.team WHERE id = (?)");
            prepStat.setLong(1, id);
            result = prepStat.executeUpdate() != 0;
            prepStat.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        if (result) {
            log.fine("Team " + id + " successfull deleted");
        }
        return result;
    }

    @Override
    public void update(Long id, Team team) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement prepStat = connection
                    .prepareStatement("UPDATE staffcontrol.team SET title = (?) WHERE id = (?)");
            prepStat.setString(1, team.getTitle());
            prepStat.setLong(2, id);
            prepStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        log.fine("Team " + id + " successfull updated");
    }

    @Override
    public Team findById(Long id) {
        Team team = new Team();
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement prepStat = connection
                    .prepareStatement("SELECT * FROM staffcontrol.team WHERE id = (?)");
            prepStat.setLong(1, id);
            resultSet = prepStat.executeQuery();
            while (resultSet.next()) {
                team.setId(resultSet.getLong(1));
                team.setTitle(resultSet.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        log.fine("Team " + id + " successfull finded");
        return team;
    }
}
