package staffcontrol.dao.impl;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import staffcontrol.dao.impl.jdbc.FeedbackDaoImpl;
import staffcontrol.entity.Feedback;
import org.junit.Test;
import staffcontrol.util.BasicConnectionPool;
import java.sql.SQLException;
import java.util.List;
import static org.junit.Assert.*;

public class FeedbackDaoImplTest {
    private static BasicConnectionPool basicConnectionPool;
    private FeedbackDaoImpl feedbackDao;

    @BeforeClass
    public static void initConnectionPool() {
        basicConnectionPool = BasicConnectionPool.create();
    }

    @Before
    public void init() {
        feedbackDao = new FeedbackDaoImpl(basicConnectionPool);
    }

    @AfterClass
    public static void destroy() throws SQLException {
        basicConnectionPool.shutdown();
    }

    @Test
    public void createFeedback() throws SQLException {
        Feedback feedback = new Feedback();
        Feedback feedbackCreatedInDB;
        feedback.setId(null);
        feedback.setDescription("new feedback");
        feedbackCreatedInDB = feedbackDao.create(feedback);
        assertNotNull(feedback.getId());
        assertEquals(feedback.getDescription(), feedbackCreatedInDB.getDescription());
        feedbackDao.remove(feedbackCreatedInDB.getId());
    }

    @Test
    public void deleteFeedback() {
        boolean deleted = true;
        Long id;
        Feedback feedback = new Feedback();
        Feedback loadedFeedback;
        feedback.setDescription("test deleteFeedback description");
        loadedFeedback = feedbackDao.create(feedback);
        id = loadedFeedback.getId();
        assertNotNull(id);
        assertEquals(deleted, feedbackDao.remove(id));
    }

    @Test
    public void update() {
        Feedback feedback = new Feedback();
        feedback.setDescription("test update description");
        feedback = feedbackDao.create(feedback);
        Long idCreated = feedback.getId();
        assertNotNull(idCreated);
        Feedback updatedFeedback = new Feedback();
        updatedFeedback.setDescription("new, updated description for old feedback");
        feedbackDao.update(idCreated, updatedFeedback);
        assertEquals(feedbackDao.findById(idCreated).getDescription(), "new, updated description for old feedback");
        feedbackDao.remove(idCreated);
    }

    @Test
    public void findById() {
        Feedback findedById;
        Feedback newFeedback = new Feedback();
        newFeedback.setDescription("test findByID description");
        assertNull(newFeedback.getId());
        newFeedback = feedbackDao.create(newFeedback);
        assertNotNull(newFeedback.getId());
        Long id = newFeedback.getId();
        findedById = feedbackDao.findById(id);
        assertEquals(findedById.getDescription(), newFeedback.getDescription());
        feedbackDao.remove(id);
    }

    @Test
    public void findAll() {
        Feedback tempFeedback = new Feedback();
        tempFeedback.setDescription("findAll test description");
        Feedback[] feedbacks = new Feedback[]{tempFeedback};
        feedbackDao.create(tempFeedback);
        List<Feedback> finded = feedbackDao.findAll();
        assertEquals(feedbackDao.findAll().get(0).getDescription(), feedbacks[0].getDescription());
        feedbackDao.remove(feedbackDao.findAll().get(0).getId());
    }
}