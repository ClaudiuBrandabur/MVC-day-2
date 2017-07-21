package ro.teamnet.zth.appl.controller;

import ro.teamnet.zth.api.annotations.Z2HController;
import ro.teamnet.zth.api.annotations.Z2HRequestMethod;
import ro.teamnet.zth.api.annotations.Z2HRequestObject;
import ro.teamnet.zth.api.annotations.Z2HRequestParam;
import ro.teamnet.zth.appl.domain.Employee;
import ro.teamnet.zth.appl.service.EmployeeService;
import ro.teamnet.zth.appl.service.EmployeeServiceImpl;
import ro.teamnet.zth.fmk.domain.HttpMethod;

import java.util.List;

@Z2HController(urlPath = "/employees")
public class EmployeeController {

    private EmployeeService employeeService;


    public EmployeeController() {
        employeeService = new EmployeeServiceImpl();
    }


    @Z2HRequestMethod(urlPath = "/all")
    public List<Employee> getAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @Z2HRequestMethod(urlPath = "/one")
    public Employee getOneEmployee(@Z2HRequestParam(name = "id") Long employeeId) {
        return employeeService.findOneEmployee(employeeId);
    }

    @Z2HRequestMethod(urlPath = "/one", methodType = HttpMethod.DELETE)
    public Boolean deleteOneEmployee(@Z2HRequestParam(name = "id") Long employeeId) {
        return employeeService.delete(employeeId);
    }

    @Z2HRequestMethod(urlPath = "/create", methodType = HttpMethod.POST)
    public Employee saveEmployee(@Z2HRequestObject Employee employee) {
        return employeeService.save(employee);
    }

    @Z2HRequestMethod(urlPath = "/edit", methodType = HttpMethod.PUT)
    public Employee updateEmployee(@Z2HRequestObject Employee employee) {
        return employeeService.update(employee);
    }

}
