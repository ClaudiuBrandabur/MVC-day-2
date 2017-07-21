package ro.teamnet.zth.appl.service;

import ro.teamnet.zth.appl.domain.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAllEmployees();

    Employee findOneEmployee(Long employeeId);

    Boolean delete(Long employeeId);

    Employee save(Employee employee);

    Employee update(Employee employee);
}
