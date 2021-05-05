package staffcontrol.servlets.feedbacks;

import staffcontrol.dao.interfaces.FeedbackDAO;
import staffcontrol.entity.Feedback;
import staffcontrol.util.ServiceManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/feedbacks")
public class FeedbackViewServlet extends HttpServlet {
    private FeedbackDAO feedbackDAO;

    @Override
    public void init() {
        feedbackDAO = ServiceManager.getInstance(getServletContext()).getFeedbackDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Feedback> feedbacks = feedbackDAO.findAll();
                request.setAttribute("feedbacks", feedbacks.toArray());
                getServletContext().getRequestDispatcher("/views/feedbacks/viewFeedback.jsp").forward(request, response);
            }

}






