package staffcontrol.servlets.feedbacks;

import staffcontrol.dao.interfaces.FeedbackDAO;
import staffcontrol.entity.Feedback;
import staffcontrol.util.SpringServiceManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/feedbacks")
public class FeedbackViewServlet extends HttpServlet {
    private FeedbackDAO feedbackDAO;

    @Override
    public void init() {
        feedbackDAO = SpringServiceManager.getInstance(getServletContext()).getFeedbackDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Feedback> feedbacks = feedbackDAO.findAll();
        request.setAttribute("feedbacks", feedbacks.toArray());
        getServletContext().getRequestDispatcher("/views/feedbacks/viewFeedbackForm.jsp").forward(request, response);
    }
}






