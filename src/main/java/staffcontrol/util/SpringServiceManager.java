package staffcontrol.util;

import lombok.Getter;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import staffcontrol.dao.interfaces.EmployeeDAO;
import staffcontrol.dao.interfaces.FeedbackDAO;
import staffcontrol.dao.interfaces.ProjectDAO;
import staffcontrol.dao.interfaces.TeamDAO;

import javax.servlet.ServletContext;

@Getter
public class SpringServiceManager {
    private final EmployeeDAO employeeDAO;
    private final FeedbackDAO feedbackDAO;
    private final ProjectDAO projectDAO;
    private final TeamDAO teamDAO;
    private final ClassPathXmlApplicationContext springContext;

    private SpringServiceManager(ClassPathXmlApplicationContext springContext) {
        this.springContext = springContext;
        this.feedbackDAO = springContext.getBean("feedbackDAO", FeedbackDAO.class);
        this.teamDAO = springContext.getBean("teamDAO", TeamDAO.class);
        this.projectDAO = springContext.getBean("projectDAO", ProjectDAO.class);
        this.employeeDAO = springContext.getBean("employeeDAO", EmployeeDAO.class);
    }

    public static synchronized SpringServiceManager getInstance(ServletContext context) {
        SpringServiceManager instance = (SpringServiceManager) context.getAttribute("SPRING_SERVICE_MANAGER");
        if (instance == null) {
            ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext("staffcontrolContext.xml");
            instance = new SpringServiceManager(springContext);
            context.setAttribute("SPRING_SERVICE_MANAGER", instance);
        }
        return instance;
    }
}
