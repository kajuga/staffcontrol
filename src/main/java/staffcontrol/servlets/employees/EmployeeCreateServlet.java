package staffcontrol.servlets.employees;

import staffcontrol.constants.ExperienceLevel;
import staffcontrol.constants.LanguageLevel;
import staffcontrol.constants.Methodology;
import staffcontrol.dao.interfaces.FeedbackDAO;
import staffcontrol.dao.interfaces.ProjectDAO;
import staffcontrol.entity.Feedback;
import staffcontrol.entity.Project;
import staffcontrol.exceptions.EntityExistException;
import staffcontrol.dao.interfaces.EmployeeDAO;
import staffcontrol.entity.Employee;
import staffcontrol.util.ServiceManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/createEmployee")
public class EmployeeCreateServlet extends HttpServlet {
    EmployeeDAO employeeDAO;
    ProjectDAO projectDAO;
    FeedbackDAO feedbackDAO;

    @Override
    public void init() throws ServletException {
        employeeDAO = ServiceManager.getInstance(getServletContext()).getEmployeeDAO();
        projectDAO = ServiceManager.getInstance(getServletContext()).getProjectDAO();
        feedbackDAO = ServiceManager.getInstance(getServletContext()).getFeedbackDAO();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Project> projects = projectDAO.findAll();
        req.setAttribute("experienceLevels", ExperienceLevel.values());
        req.setAttribute("methodology", Methodology.values());
        req.setAttribute("languageLevels", LanguageLevel.values());
        req.setAttribute("projects", projects);
        getServletContext().getRequestDispatcher("/views/employees/createEmployeeForm.jsp").forward(req, resp);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Employee employee = new Employee();
        employee.setFirstName(request.getParameter("firstName"));
        employee.setLastName(request.getParameter("lastName"));
        employee.setPhoneNumber(request.getParameter("phoneNumber"));
        employee.setEmail(request.getParameter("email"));
        employee.setSkype(request.getParameter("skype"));
        employee.setEntryDate(Date.valueOf(request.getParameter("entryDate")));
        employee.setExperience(request.getParameter("experience"));

        //TODO
        employee.setExperienceLevel(ExperienceLevel.fromKey(request.getParameter("experienceLevel")));
        employee.setLanguageLevel(LanguageLevel.fromKey(request.getParameter("languageLevel")));
        employee.setBirthDay(Date.valueOf(request.getParameter("birthDay")));
        employee.setProject(projectDAO.findById(Long.parseLong(request.getParameter("project"))));
        //TODO
        Feedback feedback = new Feedback();
        feedback.setDescription(request.getParameter("feedback"));
        employee.setFeedback(feedbackDAO.create(feedback));
        try {
            employeeDAO.create(employee);
            response.sendRedirect(request.getContextPath() + "/employees");
        } catch (EntityExistException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(e.getMessage());
        }
    }
}