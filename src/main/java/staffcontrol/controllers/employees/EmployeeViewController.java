package staffcontrol.controllers.employees;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import staffcontrol.constants.ExperienceLevel;
import staffcontrol.constants.LanguageLevel;
import staffcontrol.constants.Methodology;
import staffcontrol.dao.interfaces.EmployeeDAO;
import staffcontrol.dao.interfaces.ProjectDAO;
import staffcontrol.entity.Employee;
import staffcontrol.entity.Feedback;
import staffcontrol.entity.Project;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class EmployeeViewController {
    private final EmployeeDAO employeeDAO;
    private final ProjectDAO projectDAO;


    public EmployeeViewController(EmployeeDAO employeeDAO, ProjectDAO projectDAO) {
        this.employeeDAO = employeeDAO;

        this.projectDAO = projectDAO;
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
    protected String editEmployee(@ModelAttribute Employee employee)  {
        employeeDAO.update(employee.getId(), employee);
        return "redirect:/employees";
    }



}
