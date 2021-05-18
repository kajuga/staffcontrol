package staffcontrol.dao.spring.jdbc;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import staffcontrol.dao.interfaces.FeedbackDAO;
import staffcontrol.entity.Feedback;

import java.sql.*;
import java.util.List;

public class FeedbackDaoSpringJdbcImpl implements FeedbackDAO {

    private final JdbcTemplate jdbcTemplate;

    public FeedbackDaoSpringJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Feedback create(Feedback feedback) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO staffcontrol.feedback (description) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, feedback.getDescription());
            return ps;
        }, keyHolder);
        long newFedbackId = new Long((Integer) keyHolder.getKeyList().get(0).get("id"));
        feedback.setId(newFedbackId);
        return feedback;
    }

    @Override
    public boolean remove(Long id) {
        boolean result = false;
        try {
            jdbcTemplate.update("DELETE FROM staffcontrol.feedback WHERE id=?", id);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void update(Long id, Feedback feedback) {
        jdbcTemplate.update("UPDATE staffcontrol.feedback SET description = (?) WHERE id = (?)", feedback.getDescription(), id);
    }


    //If object field names equals table column names, we can do this:
    @Override
    public Feedback findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM staffcontrol.feedback WHERE id=?", new BeanPropertyRowMapper<>(Feedback.class), id);
    }

//    @Override
//    public Feedback findById(Long id) {
//        return jdbcTemplate.query("SELECT * FROM staffcontrol.feedback WHERE id=?", new Object[]{id}, new FeedbackRowMapper())
//                .stream().findAny().orElse(null);
//    }

//    @Override
//    public Feedback findById(Long id) {
//        return jdbcTemplate.queryForObject("SELECT * FROM staffcontrol.feedback WHERE id=?", new FeedbackRowMapper(), id);
//    }

    @Override
    public List<Feedback> findAll() {
        List<Feedback> feedbacks = jdbcTemplate.query("SELECT * FROM staffcontrol.feedback", new FeedbackRowMapper());
        return feedbacks;
    }

    private class FeedbackRowMapper implements RowMapper<Feedback> {

        @Override
        public Feedback mapRow(ResultSet resultSet, int i) throws SQLException {
            Feedback feedback = new Feedback();
            feedback.setId(resultSet.getLong(1));
            feedback.setDescription(resultSet.getString("description"));
            feedback.setCreated(resultSet.getDate(3));
            return feedback;
        }
    }
}















