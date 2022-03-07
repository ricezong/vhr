package com.gz.vhr.controller.salary;

import com.gz.vhr.bean.RespPageBean;
import com.gz.vhr.service.EmployeeService;
import com.gz.vhr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ClassName SalTableController
 * @Description TODO
 * @Author 孔明灯
 * @Data 2022/2/20 16:27
 * @Version 1.0
 */
@RestController
@RequestMapping("/salary/table")
public class SalTableController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/")
    public RespPageBean getAllEmpSalByDepId(@RequestParam(defaultValue = "1") Long page, @RequestParam(defaultValue = "10") Long size, Integer depID, String workMonth, String empName){
        return employeeService.getAllEmpSalByDepId(page,size,depID,workMonth,empName);
    }
}
