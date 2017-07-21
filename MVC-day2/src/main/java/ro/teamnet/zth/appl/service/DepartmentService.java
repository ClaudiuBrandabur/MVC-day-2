package ro.teamnet.zth.appl.service;

import ro.teamnet.zth.appl.domain.Department;

import java.util.List;

/**
 * Created by Oana.Mihai on 7/15/2016.
 */
public interface DepartmentService {

    List<Department> findAll();

    Department findOne(Long departmentId);
}
