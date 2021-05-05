package staffcontrol.servlets.employees;

import staffcontrol.exceptions.EntityExistException;
import staffcontrol.dao.interfaces.EmployeeDAO;
import staffcontrol.entity.Employee;
import staffcontrol.util.ServiceManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/createEmployee")
public class EmployeeCreateServlet extends HttpServlet {
    EmployeeDAO employeeDAO;

    @Override
    public void init() throws ServletException {
        employeeDAO = ServiceManager.getInstance(getServletContext()).getEmployeeDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Employee employee = new Employee();
        employee.setFirstName(request.getParameter("firstName"));
        employee.setLastName(request.getParameter("lastName"));
        employee.setPhoneNumber(request.getParameter("phone_number"));
        employee.setEmail(request.getParameter("email"));
        employee.setSkype(request.getParameter("skype"));
        //TODO add another thing

        }
    }

