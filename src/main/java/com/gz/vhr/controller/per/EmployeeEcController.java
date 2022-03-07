package com.gz.vhr.controller.per;


import com.gz.vhr.bean.*;
import com.gz.vhr.service.EmployeeEcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  员工奖惩
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@RestController
@RequestMapping("/personnel/ec")
public class EmployeeEcController {

    @Autowired
    EmployeeEcService employeeEcService;

    @GetMapping("/")
    public RespPageBean getEmployeeEcByPage(@RequestParam(defaultValue = "1") Long page, @RequestParam(defaultValue = "10") Long size,String empName){
        return employeeEcService.getEmployeeEcByPage(page,size,empName);
    }

    @PostMapping("/")
    public RespBean addEmpEc(@RequestBody Employee employee){
        if (employeeEcService.addEmpEc(employee)>0){
            return RespBean.success("添加成功!");
        }
        return RespBean.fail("添加失败!");
    }

    @DeleteMapping("/")
    public RespBean delEmpEc(@RequestParam("eid") Integer eid){
        if (employeeEcService.delEmpEc(eid)>0){
            return RespBean.success("删除成功!");
        }
        return RespBean.fail("删除失败!");
    }
}

