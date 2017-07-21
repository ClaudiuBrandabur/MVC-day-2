package ro.teamnet.zth.appl.dao;

import ro.teamnet.zth.api.em.EntityManager;
import ro.teamnet.zth.api.em.EntityManagerImpl;
import ro.teamnet.zth.appl.domain.Employee;

import java.util.List;

/**
 * Created by Raluca.Russindilar on 13.07.2016.
 */
public class EmployeeDao {
    EntityManager entityManager = new EntityManagerImpl();

    /**
     * @param employee
     * @return employee object
     */
    public Employee insertEmployee(Employee employee) {
        return (Employee) entityManager.insert(employee);
    }

    /**
     * @param employee
     * @return employee object
     */
    public Employee updateEmployee(Employee employee) {
        return entityManager.update(employee);
    }

    /**
     * @param employee
     */
    public void deleteEmployee(Employee employee) {
        entityManager.delete(employee);
    }

    /**
     * @return a list of employees
     */
    public List<Employee> getAllEmployees() {
        EntityManager entityManager = new EntityManagerImpl();
        return entityManager.findAll(Employee.class);
    }

    /**
     * @param id
     * @return employee object
     */
    public Employee getEmployeeById(Long id) {
        return entityManager.findById(Employee.class, id);
    }
}
