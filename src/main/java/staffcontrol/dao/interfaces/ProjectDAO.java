package staffcontrol.dao.interfaces;

import staffcontrol.entity.Project;

import java.util.List;

public interface ProjectDAO {
    Project create(Project project);
    boolean remove (Long id);
    void update(Long id, Project project);
    Project findById(Long id);
    List<Project> findAll();
}