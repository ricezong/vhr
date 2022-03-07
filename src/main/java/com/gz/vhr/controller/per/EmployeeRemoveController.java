package com.gz.vhr.controller.per;


import com.gz.vhr.bean.*;
import com.gz.vhr.service.DepartmentService;
import com.gz.vhr.service.EmployeeRemoveService;
import com.gz.vhr.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  员工调动
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@RestController
@RequestMapping("/personnel/remove")
public class EmployeeRemoveController {

    @Autowired
    EmployeeRemoveService employeeRemoveService;
    @Autowired
    PositionService positionService;
    @Autowired
    DepartmentService departmentService;


    @GetMapping("/")
    public RespPageBean getEmpMvByPage(@RequestParam(defaultValue = "1") Long page, @RequestParam(defaultValue = "10") Long size, String empName){
        return employeeRemoveService.getEmpMvByPage(page,size,empName);
    }

    /**
     * 查询所有职位
     * @return
     */
    @GetMapping("/poses")
    public List<Position> getAllPositions(){
        return positionService.getAllPositions();
    }

    /**
     * 查询所有部门
     * @return
     */
    @GetMapping("/deps")
    public List<Department> getAllDeps(){
        return departmentService.getAllDepartments();
    }

    @PostMapping("/")
    public RespBean addEmpMv(@RequestBody Employee employee){
        if (employeeRemoveService.addEmpMv(employee)>0){
            return RespBean.success("添加成功!");
        }
        return RespBean.fail("添加失败!");
    }

    @DeleteMapping("/")
    public RespBean delEmpMv(@RequestParam("eid") Integer eid){
        if (employeeRemoveService.delEmpMv(eid)>0){
            return RespBean.success("删除成功!");
        }
        return RespBean.fail("删除失败!");
    }
}

