package ro.teamnet.zth.appl;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ro.teamnet.zth.appl.dao.EmployeeDao;
import ro.teamnet.zth.appl.domain.Employee;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeDaoTest {
    static Employee employee = new Employee();
    EmployeeDao employeeDao = new EmployeeDao();

    @Test
    public void aTestInsertEmployee() {
        employee.setEmail("mail@mail.com");
        employee.setFirstName("fName");
        employee.setLastName("lName");
        employee.setJobId("AD_PRES");
        employee.setSalary(BigDecimal.ONE);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date formatedDate = simpleDateFormat.parse(simpleDateFormat.format(date));
            employee.setHireDate(new java.sql.Date(formatedDate.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        employee = employeeDao.insertEmployee(employee);

        assertEquals(employeeDao.getEmployeeById(employee.getId()), employee);
    }

    @Test
    public void bTestUpdateEmployee() {
        employee.setHireDate(new java.sql.Date(new Date().getTime()));
        employee = employeeDao.updateEmployee(employee);

        assertEquals(employeeDao.getEmployeeById(employee.getId()), employee);
    }

    @Test
    public void cTestDeleteEmployee() {
        employeeDao.deleteEmployee(employee);
        Employee locById = employeeDao.getEmployeeById(employee.getId());

        assertNull(locById);
    }

    @Test
    public void dGetAllEmployees() {
        List<Employee> oldLoc = employeeDao.getAllEmployees();
        //add new employee
        employee.setEmail("mail@mail.com");
        employee.setFirstName("fName_all");
        employee.setLastName("lName_all");
        employee.setJobId("AD_PRES");
        employee.setHireDate(new java.sql.Date(new Date().getTime()));
        employee = employeeDao.insertEmployee(employee);
        List<Employee> newLoc = employeeDao.getAllEmployees();

        assertEquals(oldLoc.size(), newLoc.size() - 1);
    }
}
