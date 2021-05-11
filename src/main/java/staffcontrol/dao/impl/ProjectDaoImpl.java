package staffcontrol.dao.impl;

import lombok.extern.java.Log;
import staffcontrol.constants.Methodology;
import staffcontrol.dao.interfaces.ProjectDAO;
import staffcontrol.dao.interfaces.TeamDAO;
import staffcontrol.entity.Project;
import staffcontrol.entity.Team;
import staffcontrol.util.BasicConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log
public class ProjectDaoImpl implements ProjectDAO {

    private final BasicConnectionPool connectionPool;
    private TeamDAO teamDAO;

    public ProjectDaoImpl(BasicConnectionPool connectionPool, TeamDAO teamDAO) {
        this.connectionPool = connectionPool;
        this.teamDAO = teamDAO;
    }

    @Override
    public Project create(Project project) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement prepStat = connection.prepareStatement(
                     "INSERT INTO staffcontrol.project (name, client, duration, methodology, project_manager, team_id) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            prepStat.setString(1, project.getName());
            prepStat.setString(2, project.getClient());
            prepStat.setString(3, project.getDuration());
            prepStat.setString(4, String.valueOf(project.getMethodology()));
            prepStat.setString(5, project.getProjectManager());
            prepStat.setLong(6, project.getTeam().getId());
            prepStat.execute();
            long idProject = -1;
            ResultSet generatedKey = prepStat.getGeneratedKeys();
            if(generatedKey.next()) {
                idProject = generatedKey.getLong(1);
                project.setId(idProject);
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        log.fine("Project " + project.getId() + " succesfull created");
        return project;
    }

    @Override
    public boolean remove(Long id) {
        boolean result = false;
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
             PreparedStatement prepStat = connection.prepareStatement("DELETE FROM staffcontrol.project WHERE id = (?)");
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
        log.fine("Project " + id + " successfull deleted");
        return result;
    }

    @Override
    public void update(Long id, Project project) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement prepStat = connection.prepareStatement("UPDATE staffcontrol.project SET name = (?), " +
                     "client=(?), duration=(?), methodology=(?), project_manager=(?), team_id=(?) WHERE id = (?)");
            prepStat.setString(1, project.getName());
            prepStat.setString(2, project.getClient());
            prepStat.setString(3, project.getDuration());
            prepStat.setString(4, String.valueOf(project.getMethodology()));
            prepStat.setString(5, project.getProjectManager());
            prepStat.setLong(6, project.getTeam().getId());
            prepStat.setLong(7, id);
            prepStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        log.fine("Project " + id + " successfull updated");
    }

    @Override
    public Project findById(Long id) {
        Project project = new Project();
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
             PreparedStatement prepStat = connection
                     .prepareStatement("SELECT * FROM staffcontrol.project WHERE id = (?)");
            prepStat.setLong(1, id);
            resultSet = prepStat.executeQuery();
            while (resultSet.next()) {
                project.setId(resultSet.getLong(1));
                project.setName(resultSet.getString(2));
                project.setClient(resultSet.getString(3));
                project.setDuration(resultSet.getString(4));
                String methodology = resultSet.getString(5);
                project.setMethodology(methodology != null ? Methodology.fromKey(methodology) : null);
                project.setProjectManager(resultSet.getString(6));
                Team team = teamDAO.findById(resultSet.getLong(7));
                project.setTeam(team);
                project.setTeam(team);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        log.fine("Project " + id + " successfull finded");
        return project;
    }


    @Override
    public List<Project> findAll() {
        List<Project> projects = new ArrayList<>();
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM staffcontrol.project");
            while (resultSet.next()) {
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
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        log.fine("All projects successfull finded");
        return projects;
    }
}


