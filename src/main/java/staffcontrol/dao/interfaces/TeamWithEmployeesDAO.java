package staffcontrol.dao.interfaces;

import staffcontrol.entity.Team;
import java.util.List;

public interface TeamWithEmployeesDAO {

    List<Team> findAllTeams();
}
