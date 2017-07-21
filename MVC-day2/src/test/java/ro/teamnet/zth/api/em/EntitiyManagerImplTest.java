package ro.teamnet.zth.api.em;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ro.teamnet.zth.appl.domain.Department;
import ro.teamnet.zth.appl.domain.Employee;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntitiyManagerImplTest {

    EntityManagerImpl entityManager = new EntityManagerImpl();
    static Department dep = new Department();

    @Test
    public void aTestFindById() {
        dep = entityManager.findById(Department.class, 10L);
        assertTrue(dep.getId() == 10L);
        assertTrue(dep.getDepartmentName().equals("Administration"));
    }

    @Test
    public void bTestFindAll() {
        List<Department> deps = entityManager.findAll(Department.class);
        assertEquals(deps.size(), 27);
    }

    @Test
    public void cTestInsert() {
        dep.setDepartmentName("Sales");
        dep.setLocation(1000l);
        dep = (Department) entityManager.insert(dep);
        assertEquals(entityManager.findById(Department.class, dep.getId()), dep);
    }

    @Test
    public void dTestUpdate() {
        dep.setId(271L);
        dep.setDepartmentName("Sales updates");
        dep.setLocation(1000l);
        dep = entityManager.update(dep);
        assertTrue(dep.getDepartmentName().equals("Sales updates"));
    }

    @Test
    public void eTestDelete() {
        dep.setId(271L);
        entityManager.delete(dep);
        assertTrue(entityManager.findById(Department.class, dep.getId()) == null);
    }

    @Test
    public void fTestFindByParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("location_id", 1700l);
        List<Department> departments = entityManager.findByParams(Department.class, params);
        assertEquals(departments.size(), 21);
    }

    /*@Test
    public void testCallableStatement(){
        entityManager.applyProcedure(100l, 25000l);
        Employee employee  = entityManager.findById(Employee.class,100l);
        assertTrue(employee.getSalary().compareTo(new BigDecimal(25000))==0);
    }*/

}
