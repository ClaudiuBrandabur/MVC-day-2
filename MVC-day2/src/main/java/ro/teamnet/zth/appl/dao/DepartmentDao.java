package ro.teamnet.zth.appl.dao;

import ro.teamnet.zth.api.em.EntityManager;
import ro.teamnet.zth.api.em.EntityManagerImpl;
import ro.teamnet.zth.appl.domain.Department;

import java.util.List;

public class DepartmentDao {

    EntityManager entityManager = new EntityManagerImpl();

    public Department findDepartmentById(Long id) {
        return (Department) entityManager.findById(Department.class, id);
    }

    public List<Department> findAllDepartments() {
        return entityManager.findAll(Department.class);
    }

    public Department insertDepartment(Department department) {
        return (Department) entityManager.insert(department);
    }

    public Department updateDepartment(Department department) {
        return entityManager.update(department);
    }

    public void deleteDepartment(Department department) {
        entityManager.delete(department);
    }
}
