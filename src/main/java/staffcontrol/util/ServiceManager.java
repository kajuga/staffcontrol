package staffcontrol.util;

import lombok.Getter;
import staffcontrol.dao.impl.jdbc.EmployeeDaoImpl;
import staffcontrol.dao.impl.jdbc.FeedbackDaoImpl;
import staffcontrol.dao.impl.jdbc.ProjectDaoImpl;
import staffcontrol.dao.impl.jdbc.TeamDaoImpl;
import staffcontrol.dao.interfaces.EmployeeDAO;
import staffcontrol.dao.interfaces.FeedbackDAO;
import staffcontrol.dao.interfaces.ProjectDAO;
import staffcontrol.dao.interfaces.TeamDAO;

import javax.servlet.ServletContext;

@Getter
public class ServiceManager {
    private final EmployeeDAO employeeDAO;
    private final FeedbackDAO feedbackDAO;
    private final ProjectDAO projectDAO;
    private final TeamDAO teamDAO;

    private ServiceManager(BasicConnectionPool connectionPool) {
        this.feedbackDAO = new FeedbackDaoImpl(connectionPool);
        this.teamDAO = new TeamDaoImpl(connectionPool);
        this.projectDAO = new ProjectDaoImpl(connectionPool, teamDAO);
        this.employeeDAO = new EmployeeDaoImpl(connectionPool, projectDAO, feedbackDAO);
    }

    public static synchronized ServiceManager getInstance(ServletContext context) {
        ServiceManager instance = (ServiceManager) context.getAttribute("SERVICE_MANAGER");
        if (instance == null) {
            BasicConnectionPool connectionPool = BasicConnectionPool.create();
            instance = new ServiceManager(connectionPool);
            context.setAttribute("SERVICE_MANAGER", instance);
        }
        return instance;
    }
}
