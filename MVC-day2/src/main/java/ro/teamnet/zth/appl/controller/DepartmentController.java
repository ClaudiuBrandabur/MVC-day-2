package ro.teamnet.zth.appl.controller;

import ro.teamnet.zth.api.annotations.Z2HController;
import ro.teamnet.zth.api.annotations.Z2HRequestMethod;
import ro.teamnet.zth.api.annotations.Z2HRequestParam;
import ro.teamnet.zth.appl.domain.Department;
import ro.teamnet.zth.appl.service.DepartmentService;
import ro.teamnet.zth.appl.service.DepartmentServiceImpl;

import java.util.List;

@Z2HController(urlPath = "/departments")
public class DepartmentController {

    private DepartmentService departmentService;

    public DepartmentController() {
        departmentService = new DepartmentServiceImpl();
    }

    @Z2HRequestMethod(urlPath = "/all")
    public List<Department> getAllDepartments() {
        return departmentService.findAll();
    }

    @Z2HRequestMethod(urlPath = "/one")
    public Department getOneDepartment(@Z2HRequestParam(name = "id") Long departmentId) {
        return departmentService.findOne(departmentId);
    }
}
