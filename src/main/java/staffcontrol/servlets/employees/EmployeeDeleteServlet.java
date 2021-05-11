package staffcontrol.servlets.employees;


import staffcontrol.dao.interfaces.EmployeeDAO;
import staffcontrol.util.ServiceManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteEmployee")
public class EmployeeDeleteServlet extends HttpServlet {
    EmployeeDAO employeeDAO;

    @Override
    public void init() throws ServletException {
        employeeDAO = ServiceManager.getInstance(getServletContext()).getEmployeeDAO();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long employeeId = Long.parseLong(req.getParameter("employeeId"));
        employeeDAO.remove(employeeId);
        resp.sendRedirect("/staffcontrol/employees");
    }












//TODO
}