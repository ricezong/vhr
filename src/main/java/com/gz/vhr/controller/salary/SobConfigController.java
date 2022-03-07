package com.gz.vhr.controller.salary;

import com.gz.vhr.bean.RespBean;
import com.gz.vhr.bean.RespPageBean;
import com.gz.vhr.bean.Salary;
import com.gz.vhr.service.EmployeeService;
import com.gz.vhr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SobConfigController
 * @Description 员工账套设置
 * @Author 孔明灯
 * @Data 2021/9/20 20:58
 * @Version 1.0
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SobConfigController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    SalaryService salaryService;

    @GetMapping("/")
    public RespPageBean getEmployeeByPageWithSalary(@RequestParam(defaultValue = "1") Long page,@RequestParam(defaultValue = "10") Long size){
        return employeeService.getEmployeeByPageWithSalary(page,size);
    }

    @GetMapping("/salaries")
    public List<Salary> getAllSalaries(){
        return salaryService.getAllSalaries();
    }

    @PutMapping("/")
    public RespBean updateEmpSalById(Integer eid,Integer sid){
        if (employeeService.updateEmpSalById(eid,sid)>0){
            return RespBean.success("更新成功");
        }
        return RespBean.fail("更新失败");
    }
}
