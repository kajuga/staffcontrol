package staffcontrol.dao.impl;

import staffcontrol.dao.interfaces.FeedbackDAO;
import staffcontrol.entity.Feedback;
import staffcontrol.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDaoImpl implements FeedbackDAO{
    private final ConnectionUtil connectionUtil;

    public FeedbackDaoImpl(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    @Override
    public Feedback create(Feedback feedback) {
        try (Connection connection = connectionUtil.getConnection();
            PreparedStatement prepStat = connection
                    .prepareStatement("INSERT INTO staffcontrol.feedback (description) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            prepStat.setString(1, feedback.getDescription());
            prepStat.executeUpdate();
            int idFeedback = -1;
            ResultSet generatedKeys = prepStat.getGeneratedKeys();
            if(generatedKeys.next()) {
                idFeedback = generatedKeys.getInt(1);
                feedback.setId((long) idFeedback);
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return feedback;
    }

    @Override
    public boolean remove(Long id) {
        boolean result = false;
        try (Connection connection = connectionUtil.getConnection();
        PreparedStatement prepStat = connection
                .prepareStatement("DELETE FROM staffcontrol.feedback WHERE id = (?)")) {
            prepStat.setLong(1, id);
            result = prepStat.executeUpdate() != 0;
            prepStat.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return result;
    }

    @Override
    public void update(Long id, Feedback feedback) {
        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement prepStat = connection
                     .prepareStatement("UPDATE staffcontrol.feedback SET description = (?) WHERE id = (?)")) {
            prepStat.setString(1, feedback.getDescription());
            prepStat.setLong(2, id);
            prepStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Feedback findById(Long id) {
        Feedback feedback = new Feedback();
        ResultSet resultSet;
        try (Connection connection = connectionUtil.getConnection();
             PreparedStatement prepStat = connection
                .prepareStatement("SELECT * FROM staffcontrol.feedback WHERE id = ?")) {
            prepStat.setLong(1, id);
            resultSet = prepStat.executeQuery();
            while (resultSet.next()) {
                feedback.setId(resultSet.getLong(1));
                feedback.setDescription(resultSet.getString("description"));
                feedback.setCreated(resultSet.getDate(3).getTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedback;
    }

    @Override
    public List<Feedback> findAll() {
        List<Feedback> feedbacks = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = connectionUtil.getConnection();
            Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM staffcontrol.feedback");
            while (resultSet.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(resultSet.getLong(1));
                feedback.setDescription(resultSet.getString("description"));
                feedback.setCreated(resultSet.getDate(3).getTime());
                feedbacks.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbacks;
    }
}
