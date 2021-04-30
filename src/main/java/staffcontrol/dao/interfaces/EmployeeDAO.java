package staffcontrol.dao.interfaces;

import staffcontrol.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    Employee create(Employee employee);
    boolean remove (Long id);
    void update(Long id, Employee employee);
    Employee findById(Long id);
}