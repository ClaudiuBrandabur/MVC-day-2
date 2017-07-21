package ro.teamnet.zth.appl.service;

import ro.teamnet.zth.appl.dao.EmployeeDao;
import ro.teamnet.zth.appl.domain.Employee;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao = new EmployeeDao();

    @Override
    public List<Employee> findAllEmployees() {
        return employeeDao.getAllEmployees();
    }

    @Override
    public Employee findOneEmployee(Long employeeId) {
        return employeeDao.getEmployeeById(employeeId);
    }

    @Override
    public Boolean delete(Long employeeId) {
        Employee employeeToDelete = employeeDao.getEmployeeById(employeeId);
        if (employeeToDelete == null) {
            return false;
        }
        employeeDao.deleteEmployee(employeeToDelete);
        return true;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeDao.insertEmployee(employee);
    }

    @Override
    public Employee update(Employee employee) {
        return employeeDao.updateEmployee(employee);
    }
}
