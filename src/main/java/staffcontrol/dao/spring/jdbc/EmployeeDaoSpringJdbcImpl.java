package staffcontrol.dao.spring.jdbc;

import lombok.extern.java.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import staffcontrol.constants.ExperienceLevel;
import staffcontrol.constants.LanguageLevel;
import staffcontrol.dao.interfaces.EmployeeDAO;
import staffcontrol.dao.interfaces.FeedbackDAO;
import staffcontrol.dao.interfaces.ProjectDAO;
import staffcontrol.entity.Employee;
import staffcontrol.entity.Feedback;
import staffcontrol.entity.Project;

import java.sql.*;
import java.util.List;

@Component
@Log
public class EmployeeDaoSpringJdbcImpl implements EmployeeDAO {
    private final JdbcTemplate jdbcTemplate;
    private final ProjectDAO projectDao;
    private final FeedbackDAO feedbackDAO;


    public EmployeeDaoSpringJdbcImpl(JdbcTemplate jdbcTemplate, ProjectDAO projectDao,
                                     FeedbackDAO feedbackDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.projectDao = projectDao;
        this.feedbackDAO = feedbackDAO;
    }

    @Override
    public Employee create(Employee employee) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement prepStat = con.prepareStatement(
                    "INSERT INTO staffcontrol.employee (first_name, last_name, phone_number, email, skype, entry_date, experience, experience_level, language_level, birthday, project_id, feedback_id) " +
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
            return prepStat;
        }, keyHolder);
        long newEmployeeId = new Long((Integer) keyHolder.getKeyList().get(0).get("id"));
        employee.setId(newEmployeeId);
        return employee;
    }


    @Override
    public boolean remove(Long id) {
        boolean result = false;
        try {
            jdbcTemplate.update("DELETE FROM staffcontrol.employee WHERE id=?", id);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void update(Long id, Employee employee) {
        jdbcTemplate.update("UPDATE staffcontrol.employee SET first_name = (?), last_name = (?), phone_number = (?)," +
                        "email = (?), skype = (?), entry_date = (?), experience = (?), experience_level = (?), language_level = (?), " +
                        "birthday = (?), project_id = (?), feedback_id = (?) WHERE id = (?)",
                employee.getFirstName(), employee.getLastName(), employee.getPhoneNumber(), employee.getEmail(),
                employee.getSkype(), employee.getEntryDate(), employee.getExperience(), String.valueOf(employee.getExperienceLevel()),
                String.valueOf(employee.getLanguageLevel()), employee.getBirthDay(), employee.getProject().getId(),
                employee.getFeedback().getId(), id);
    }

    @Override
    public Employee findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM staffcontrol.employee WHERE id=?", new EmployeeRowMapper(), id);
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = jdbcTemplate.query("SELECT * FROM staffcontrol.employee", new EmployeeRowMapper());
        return employees;
    }

    private class EmployeeRowMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
            Employee employee = new Employee();
            employee.setId(resultSet.getLong(1));
            employee.setFirstName(resultSet.getString(2));
            employee.setLastName(resultSet.getString(3));
            employee.setPhoneNumber(resultSet.getString(4));
            employee.setEmail(resultSet.getString(5));
            employee.setSkype(resultSet.getString(6));
            employee.setEntryDate(resultSet.getDate(7));
            employee.setExperience(resultSet.getString(8));
            String experienceLevel = resultSet.getString("experience_level");
            employee.setExperienceLevel(experienceLevel != null ? ExperienceLevel.fromKey(experienceLevel) : null);
            String languageLevel = resultSet.getString("language_level");
            employee.setLanguageLevel(languageLevel != null ? LanguageLevel.fromKey(languageLevel) : null);
            employee.setBirthDay(resultSet.getDate(11));
            Project project = projectDao.findById(resultSet.getLong(12));
            employee.setProject(project);
            Feedback feedback = feedbackDAO.findById(resultSet.getLong(13));
            employee.setFeedback(feedback);
            return employee;
        }
    }
}