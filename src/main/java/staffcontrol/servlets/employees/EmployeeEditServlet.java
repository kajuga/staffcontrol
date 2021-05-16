package staffcontrol.servlets.employees;


import staffcontrol.constants.ExperienceLevel;
import staffcontrol.constants.LanguageLevel;
import staffcontrol.constants.Methodology;
import staffcontrol.dao.interfaces.EmployeeDAO;
import staffcontrol.dao.interfaces.FeedbackDAO;
import staffcontrol.dao.interfaces.ProjectDAO;
import staffcontrol.entity.Employee;
import staffcontrol.entity.Feedback;
import staffcontrol.entity.Project;
import staffcontrol.exceptions.EntityExistException;
import staffcontrol.util.SpringServiceManager;

import java.sql.Date;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/editEmployee")
public class EmployeeEditServlet extends HttpServlet {
    EmployeeDAO employeeDAO;
    ProjectDAO projectDAO;
    FeedbackDAO feedbackDAO;

    @Override
    public void init() throws ServletException {
        employeeDAO = SpringServiceManager.getInstance(getServletContext()).getEmployeeDAO();
        projectDAO = SpringServiceManager.getInstance(getServletContext()).getProjectDAO();
        feedbackDAO = SpringServiceManager.getInstance(getServletContext()).getFeedbackDAO();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long employeeId = Long.parseLong(request.getParameter("employeeId"));
        Employee employee = employeeDAO.findById(employeeId);
        request.setAttribute("employee", employee);
        List<Project> projects = projectDAO.findAll();
        request.setAttribute("experienceLevels", ExperienceLevel.values());
        request.setAttribute("methodology", Methodology.values());
        request.setAttribute("languageLevels", LanguageLevel.values());
        request.setAttribute("projects", projects);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/employees/updateEmployeeForm.jsp");
        dispatcher.forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Employee employee = new Employee();
        Long employeeId = Long.parseLong(request.getParameter("id"));
        employee.setFirstName(request.getParameter("firstName"));
        employee.setLastName(request.getParameter("lastName"));
        employee.setPhoneNumber(request.getParameter("phoneNumber"));
        employee.setEmail(request.getParameter("email"));
        employee.setSkype(request.getParameter("skype"));
        employee.setEntryDate(Date.valueOf(request.getParameter("entryDate")));
        employee.setExperience(request.getParameter("experience"));
        employee.setExperienceLevel(ExperienceLevel.fromKey(request.getParameter("experienceLevel")));
        employee.setLanguageLevel(LanguageLevel.fromKey(request.getParameter("languageLevel")));
        employee.setBirthDay(Date.valueOf(request.getParameter("birthDay")));

        employee.setProject(projectDAO.findById(Long.parseLong(request.getParameter("project"))));

        Feedback feedback = new Feedback();
        feedback.setDescription(request.getParameter("feedback"));
        employee.setFeedback(feedbackDAO.create(feedback));
        try {
            employeeDAO.update(employeeId, employee);
            response.sendRedirect(request.getContextPath() + "/employees");
        } catch (EntityExistException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(e.getMessage());
        }
    }
}