package staffcontrol.dao.interfaces;

import staffcontrol.entity.Feedback;

import java.util.List;

public interface FeedbackDAO {
    Feedback create(Feedback feedback);
    boolean remove (Long id);
    void update (Long id, Feedback feedback);
    Feedback findById(Long id);
    List<Feedback> findAll();
}
