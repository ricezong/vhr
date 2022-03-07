package com.gz.vhr.controller.sta;

import com.gz.vhr.bean.Employee;
import com.gz.vhr.bean.EmployeeEc;
import com.gz.vhr.bean.RespPageBean;
import com.gz.vhr.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName StaScoreController
 * @Description TODO
 * @Author 孔明灯
 * @Data 2022/3/7 17:50
 * @Version 1.0
 */
@RestController
@RequestMapping("/statistics/score")
public class StaScoreController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/dep")
    public List<Employee> getALlEmpByDepID(Integer depID){
        return employeeService.getALlEmpByDepID(depID);
    }

    @GetMapping("/emp")
    public List<EmployeeEc> getEmpEcPointByEmpID(Integer empID){
        return employeeService.getEmpEcPointByEmpID(empID);
    }
}
