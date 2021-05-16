package staffcontrol.servlets.staff;

import staffcontrol.dao.interfaces.EmployeeDAO;
import staffcontrol.util.SpringServiceManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StaffViewServlet extends HttpServlet {

    private EmployeeDAO employeeDAO;


    @Override
    public void init() throws ServletException {
        SpringServiceManager serviceManager = SpringServiceManager.getInstance(getServletContext());
        employeeDAO = serviceManager.getEmployeeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("staff", employeeDAO.findAll());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/staff/viewStaff.jsp");
        dispatcher.forward(req, resp);
    }
}