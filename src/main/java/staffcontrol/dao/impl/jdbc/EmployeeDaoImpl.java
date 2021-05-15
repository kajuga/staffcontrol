package staffcontrol.dao.impl.jdbc;

import lombok.extern.java.Log;
import staffcontrol.constants.ExperienceLevel;
import staffcontrol.constants.LanguageLevel;
import staffcontrol.dao.interfaces.EmployeeDAO;
import staffcontrol.dao.interfaces.FeedbackDAO;
import staffcontrol.dao.interfaces.ProjectDAO;
import staffcontrol.entity.Employee;
import staffcontrol.util.BasicConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log
public class EmployeeDaoImpl implements EmployeeDAO {
    private final BasicConnectionPool connectionPool;
    private ProjectDAO projectDAO;
    private FeedbackDAO feedbackDAO;

    public EmployeeDaoImpl(BasicConnectionPool connectionPool, ProjectDAO projectDAO, FeedbackDAO feedbackDAO) {
        this.connectionPool = connectionPool;
        this.projectDAO = projectDAO;
        this.feedbackDAO = feedbackDAO;
    }


    @Override
    public Employee create(Employee employee) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement prepStat = connection.prepareStatement("INSERT INTO staffcontrol.employee (first_name, last_name, phone_number, email, skype, entry_date, experience, experience_level, language_level, birthday, project_id, feedback_id) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            prepStat.setString(1, employee.getFirstName());
            prepStat.setString(2, employee.getLastName());
            prepStat.setString(3, employee.getPhoneNumber());
            prepStat.setString(4, employee.getEmail());
            prepStat.setString(5, employee.getSkype());
            prepStat.setDate(6, employee.getEntryDate());
            prepStat.setString(7, employee.getExperience());
            prepStat.setString(8, String.valueOf(employee.getExperienceLevel()));
            prepStat.setString(9, String.valueOf(employee.getLanguageLevel()));
            prepStat.setDate(10, employee.getBirthDay());
            prepStat.setLong(11, employee.getProject().getId());
            prepStat.setLong(12, employee.getFeedback().getId());
            prepStat.execute();
            int idEmployee = -1;
            ResultSet generatedKeys = prepStat.getGeneratedKeys();
            if (generatedKeys.next()) {
                idEmployee = generatedKeys.getInt(1);
                employee.setId((long) idEmployee);
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        log.fine("Employee " + employee.getFirstName() + " " + employee.getLastName() + " succesfull created");
        return employee;
    }

    @Override
    public boolean remove(Long id) {
        boolean result = false;
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement prepStat = connection.prepareStatement("DELETE FROM staffcontrol.employee WHERE id = (?)");
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
        log.fine("Employee #" + id + " succesfull deleted");
        return result;
    }

    @Override
    public void update(Long id, Employee employee) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement prepStat = connection.prepareStatement("UPDATE staffcontrol.employee SET " +
                    "first_name = (?), last_name = (?), phone_number = (?), email = (?), skype = (?), entry_date = (?), " +
                    "experience = (?), experience_level = (?), language_level = (?), birthday = (?), project_id = (?), feedback_id = (?) WHERE id = (?)");
            prepStat.setString(1, employee.getFirstName());
            prepStat.setString(2, employee.getLastName());
            prepStat.setString(3, employee.getPhoneNumber());
            prepStat.setString(4, employee.getEmail());
            prepStat.setString(5, employee.getSkype());
            prepStat.setDate(6, employee.getEntryDate());
            prepStat.setString(7, employee.getExperience());
            prepStat.setString(8, String.valueOf(employee.getExperienceLevel()));
            prepStat.setString(9, String.valueOf(employee.getLanguageLevel()));
            prepStat.setDate(10, employee.getBirthDay());
            prepStat.setLong(11, employee.getProject().getId());
            prepStat.setLong(12, employee.getFeedback().getId());
            prepStat.setLong(13, id);
            prepStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        log.fine("Employee " + id + " successfull updated");
    }

    @Override
    public Employee findById(Long id) {
        Employee employee = new Employee();
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement prepStat = connection.prepareStatement("SELECT * FROM staffcontrol.employee WHERE id = (?)");
            prepStat.setLong(1, id);
            resultSet = prepStat.executeQuery();
            while (resultSet.next()) {
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setPhoneNumber(resultSet.getString("phone_number"));
                employee.setEmail(resultSet.getString("email"));
                employee.setSkype(resultSet.getString("skype"));
                employee.setEntryDate(resultSet.getDate(7));
                employee.setExperience(resultSet.getString(8));
                String experienceLevel = resultSet.getString("experience_level");
                employee.setExperienceLevel(experienceLevel != null ? ExperienceLevel.fromKey(experienceLevel) : null);
                String languageLevel = resultSet.getString("language_level");
                employee.setLanguageLevel(languageLevel != null ? LanguageLevel.fromKey(languageLevel) : null);
                employee.setBirthDay(resultSet.getDate("birthday"));
                employee.setProject(projectDAO.findById(resultSet.getLong("project_id")));
                employee.setFeedback(feedbackDAO.findById(resultSet.getLong("feedback_id")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        log.fine("Employee " + employee.getId() + " successfull finded");
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> stuff = new ArrayList<>();
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM staffcontrol.employee");
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setPhoneNumber(resultSet.getString("phone_number"));
                employee.setEmail(resultSet.getString("email"));
                employee.setSkype(resultSet.getString("skype"));
                employee.setEntryDate(resultSet.getDate(7));
                employee.setExperience(resultSet.getString(8));
                String experienceLevel = resultSet.getString("experience_level");
                employee.setExperienceLevel(experienceLevel != null ? ExperienceLevel.fromKey(experienceLevel) : null);
                String languageLevel = resultSet.getString("language_level");
                employee.setLanguageLevel(languageLevel != null ? LanguageLevel.fromKey(languageLevel) : null);
                employee.setBirthDay(resultSet.getDate(11));
                employee.setProject(projectDAO.findById(resultSet.getLong("project_id")));
                employee.setFeedback(feedbackDAO.findById(resultSet.getLong("feedback_id")));
                stuff.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        log.fine("ALL Employee successfull finded");
        return stuff;
    }
}
