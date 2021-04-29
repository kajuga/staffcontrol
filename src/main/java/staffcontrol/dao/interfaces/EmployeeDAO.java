package staffcontrol.dao.interfaces;

import staffcontrol.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    void add(Employee employee);
    void remove (Long id);
    void update(Long id, Employee employee);
    Employee findById(Long id);
    List<Employee> findByProjectId();
}