package staffcontrol.dao.impl;

import staffcontrol.dao.interfaces.TeamDAO;
import staffcontrol.entity.Feedback;
import staffcontrol.entity.Team;
import staffcontrol.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDaoImpl implements TeamDAO {
    private final ConnectionUtil connectionUtil;

    public TeamDaoImpl(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    @Override
    public Team create(Team team) {
        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement prepStat = connection
                     .prepareStatement("INSERT INTO staffcontrol.team (title) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            prepStat.setString(1, team.getTitle());
            prepStat.executeUpdate();
            int idTeam = -1;
            ResultSet generatedKeys = prepStat.getGeneratedKeys();
            if(generatedKeys.next()) {
                idTeam = generatedKeys.getInt(1);
                team.setId(idTeam);
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return team;
    }

    @Override
    public boolean remove(Long id) {
        boolean result = false;
        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement prepStat = connection
                     .prepareStatement("DELETE FROM staffcontrol.team WHERE id = (?)")) {
            prepStat.setLong(1, id);
            result = prepStat.executeUpdate() != 0;
            prepStat.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return result;
    }

    @Override
    public void update(Long id, Team team) {
        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement prepStat = connection
                     .prepareStatement("UPDATE staffcontrol.team SET title = (?) WHERE id = (?)")) {
            prepStat.setString(1, team.getTitle());
            prepStat.setLong(2, id);
            prepStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Team findById(Long id) {
        Team team = new Team();
        ResultSet resultSet;
        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement prepStat = connection
                     .prepareStatement("SELECT * FROM staffcontrol.team WHERE id = ?")) {
            prepStat.setLong(1, id);
            resultSet = prepStat.executeQuery();
            while (resultSet.next()) {
                team.setId(resultSet.getLong(1));
                team.setTitle(resultSet.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return team;
    }
}
