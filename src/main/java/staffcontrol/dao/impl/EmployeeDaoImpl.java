package staffcontrol.dao.impl;

import lombok.extern.java.Log;
import staffcontrol.constants.ExperienceLevel;
import staffcontrol.constants.LanguageLevel;
import staffcontrol.dao.interfaces.EmployeeDAO;
import staffcontrol.dao.interfaces.FeedbackDAO;
import staffcontrol.dao.interfaces.ProjectDAO;
import staffcontrol.entity.Employee;
import staffcontrol.util.BasicConnectionPool;
import staffcontrol.util.ConnectionUtil;
import java.sql.*;

@Log
public class EmployeeDaoImpl implements EmployeeDAO {

    private final BasicConnectionPool connectionPool;
    private PreparedStatement prepStat;
    private ProjectDAO projectDAO;
    private FeedbackDAO feedbackDAO;

    public EmployeeDaoImpl(BasicConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }


    @Override
    public Employee create(Employee employee) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement prepStat = connection
                     .prepareStatement("INSERT INTO staffcontrol.employee (first_name, last_name, phone_number, email, skype, entry_date, experience, experience_level, language_level, birthday, project_id, feedback_id) " +
                             "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            prepStat.setString(1, employee.getFirstName());
            prepStat.setString(2, employee.getLastName());
            prepStat.setString(3, employee.getPhoneNumber());
            prepStat.setString(4, employee.getEmail());
            prepStat.setString(5, employee.getSkype());
            prepStat.setDate(6, new Date(employee.getEntryDate()));
            prepStat.setString(7, employee.getExperience());
            prepStat.setString(8, String.valueOf(employee.getExperienceLevel()));
            prepStat.setString(9, String.valueOf(employee.getLanguageLevel()));
            prepStat.setDate(10, new Date(employee.getBirthDay()));
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
        }
        log.fine("Employee " + employee.getFirstName() + " " + employee.getLastName() + " succesfull created");
        return employee;
    }

    @Override
    public boolean remove(Long id) {
        boolean result = false;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement prepStat = connection
                     .prepareStatement("DELETE FROM staffcontrol.employee WHERE id = (?)")) {
            prepStat.setLong(1, id);
            result = prepStat.executeUpdate() != 0;
            prepStat.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        log.fine("Employee #" + id + " succesfull deleted");
        return result;
    }

    @Override
    public void update(Long id, Employee employee) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement prepStat = connection.prepareStatement("UPDATE staffcontrol.employee SET " +
                     "first_name = (?), last_name = (?), phone_number = (?), email = (?), skype = (?), entry_date = (?), " +
                     "experience = (?), experience_level = (?), language_level = (?), birthday = (?), project_id = (?), feedback_id = (?) WHERE id = (?)")) {
            prepStat.setString(1, employee.getFirstName());
            prepStat.setString(2, employee.getLastName());
            prepStat.setString(3, employee.getPhoneNumber());
            prepStat.setString(4, employee.getEmail());
            prepStat.setString(5, employee.getSkype());
            prepStat.setLong(6, employee.getEntryDate());
            prepStat.setObject(7, employee.getExperience());
            prepStat.setObject(8, employee.getExperienceLevel());
            prepStat.setObject(9, employee.getLanguageLevel());
            prepStat.setLong(10, employee.getBirthDay());
            prepStat.setObject(11, employee.getProject());
            prepStat.setObject(12, employee.getFeedback());
            prepStat.setLong(13, id);
            prepStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.fine("Employee " + id + " successfull updated");
    }

    @Override
    public Employee findById(Long id) {
        Employee employee = new Employee();
        ResultSet resultSet;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement prepStat = connection
                     .prepareStatement("SELECT * FROM staffcontrol.employee WHERE id = (?)")) {
            prepStat.setLong(1, id);
            resultSet = prepStat.executeQuery();
            while (resultSet.next()) {
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setPhoneNumber(resultSet.getString("phone_number"));
                employee.setEmail(resultSet.getString("email"));
                employee.setSkype(resultSet.getString("skype"));
                employee.setEntryDate(resultSet.getDate(7).getTime());
                employee.setExperience(resultSet.getString(8));
                String experienceLevel = resultSet.getString("experience_level");
                employee.setExperienceLevel(experienceLevel != null ? ExperienceLevel.fromString(experienceLevel) : null);
                String languageLevel = resultSet.getString("language_level");
                employee.setLanguageLevel(languageLevel != null ? LanguageLevel.fromString(experienceLevel) : null);
                employee.setBirthDay(resultSet.getDate(11).getTime());
                employee.setProject(projectDAO.findById(resultSet.getLong("project_id")));
                employee.setFeedback(feedbackDAO.findById(resultSet.getLong("feedback_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.fine("Employee " + employee.getId() + " successfull finded");
        return employee;
    }
}
