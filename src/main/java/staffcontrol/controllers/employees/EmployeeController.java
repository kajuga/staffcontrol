package staffcontrol.controllers.employees;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import staffcontrol.constants.ExperienceLevel;
import staffcontrol.constants.LanguageLevel;
import staffcontrol.constants.Methodology;
import staffcontrol.dao.interfaces.EmployeeDAO;
import staffcontrol.dao.interfaces.FeedbackDAO;
import staffcontrol.dao.interfaces.ProjectDAO;
import staffcontrol.entity.Employee;
import staffcontrol.entity.Project;
import java.util.List;

@Controller
public class EmployeeController {
    private final EmployeeDAO employeeDAO;
    private final ProjectDAO projectDAO;
    private final FeedbackDAO feedbackDAO;

    public EmployeeController(EmployeeDAO employeeDAO, ProjectDAO projectDAO, FeedbackDAO feedbackDAO) {
        this.employeeDAO = employeeDAO;
        this.projectDAO = projectDAO;
        this.feedbackDAO = feedbackDAO;
    }

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    protected String getEmployees(Model model) {
        List<Employee> employees = employeeDAO.findAll();
        model.addAttribute("employees", employees.toArray());
        return "/employees/viewEmployeeForm";
    }

    @RequestMapping(value = "/editEmployee", method = RequestMethod.GET)
    protected String getEditEmployee(@RequestParam("employeeId") Long employeeId, Model model) {
        Employee employee = employeeDAO.findById(employeeId);
        model.addAttribute("employee", employee);
        List<Project> projects = projectDAO.findAll();
        model.addAttribute("experienceLevels", ExperienceLevel.values());
        model.addAttribute("methodology", Methodology.values());
        model.addAttribute("languageLevels", LanguageLevel.values());
        model.addAttribute("projects", projects);
        return "/employees/updateEmployeeForm";
    }

    @RequestMapping(value = "/editEmployee", method = RequestMethod.POST)
    protected String editEmployee(@ModelAttribute Employee employee) {
        feedbackDAO.update(employee.getFeedback().getId(), employee.getFeedback());
        employeeDAO.update(employee.getId(), employee);
        return "redirect:/employees";
    }

    @RequestMapping(value = "/deleteEmployee", method = RequestMethod.GET)
    protected String deleteEmployee(@RequestParam("employeeId") Long employeeId) {
        employeeDAO.remove(employeeId);
        return "redirect:/employees";
    }

    @RequestMapping(value = "/createEmployee", method = RequestMethod.GET)
    public String showFormAdd(Model model) {
        model.addAttribute("employee", new Employee());
        List<Project> projects = projectDAO.findAll();
        model.addAttribute("experienceLevels", ExperienceLevel.values());
        model.addAttribute("methodology", Methodology.values());
        model.addAttribute("languageLevels", LanguageLevel.values());
        model.addAttribute("projects", projects);
        return "/employees/createEmployeeForm";
    }

    @RequestMapping(value = "/createEmployee", method = RequestMethod.POST)
    protected String createEmployee(@ModelAttribute Employee employee) {
        feedbackDAO.create(employee.getFeedback());
        employeeDAO.create(employee);
        return "redirect:/employees";
    }


}
