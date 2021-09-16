package com.gz.vhr.controller.system.basic;


import com.gz.vhr.bean.Department;
import com.gz.vhr.bean.RespBean;
import com.gz.vhr.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  部门管理
 *
 * @author zong
 * @since 2021-07-31
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @PostMapping("/")
    public RespBean addDep(@RequestBody Department department){
        departmentService.addDep(department);
        if (department.getResult()==1){
            return RespBean.success("添加成功",department);
        }
        return RespBean.fail("添加失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteDepById(@PathVariable Integer id) {
        Department dep = new Department();
        dep.setId(id);
        departmentService.deleteDepById(dep);
        if (dep.getResult() == -2) {
            return RespBean.fail("该部门下有子部门，删除失败");
        } else if (dep.getResult() == -1) {
            return RespBean.fail("该部门下有员工，删除失败");
        } else if (dep.getResult() == 1) {
            return RespBean.success("删除成功");
        }
        return RespBean.fail("删除失败");
    }
}

