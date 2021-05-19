package staffcontrol.servlets.employees;

import staffcontrol.dao.interfaces.EmployeeDAO;
import staffcontrol.entity.Employee;
import staffcontrol.util.SpringServiceManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class EmployeeViewServlet extends HttpServlet {
    private EmployeeDAO employeeDAO;

    @Override
    public void init() throws ServletException {
        employeeDAO = SpringServiceManager.getInstance(getServletContext()).getEmployeeDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Employee> employees = employeeDAO.findAll();
        request.setAttribute("employees", employees.toArray());
        getServletContext().getRequestDispatcher("/views/employees/viewEmployeeForm.jsp").forward(request, response);
    }
}
