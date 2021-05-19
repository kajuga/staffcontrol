package staffcontrol.controllers.feedbacks;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import staffcontrol.dao.interfaces.FeedbackDAO;
import staffcontrol.entity.Feedback;

import java.util.List;

@Controller
public class FeddbackController {
    private final FeedbackDAO feedbackDAO;

    public FeddbackController(FeedbackDAO feedbackDAO) {
        this.feedbackDAO = feedbackDAO;
    }


    @RequestMapping(value = "/feedbacks", method = RequestMethod.GET)
    protected String getFeedbacks(Model model) {
        List<Feedback> feedbacks = feedbackDAO.findAll();
        model.addAttribute("feedbacks", feedbacks.toArray());
        return "/feedbacks/viewFeedbackForm";
    }
}
