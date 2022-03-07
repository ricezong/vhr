package com.gz.vhr.controller.per;

import com.gz.vhr.bean.Employee;
import com.gz.vhr.bean.RespBean;
import com.gz.vhr.bean.RespPageBean;
import com.gz.vhr.service.AdjustSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName AdJustController
 * @Description 调薪资料
 * @Author 孔明灯
 * @Data 2022/2/18 9:43
 * @Version 1.0
 */
@RestController
@RequestMapping("/personnel/salary")
public class EmployeeAdJustController {

    @Autowired
    AdjustSalaryService adjustSalaryService;

    @GetMapping("/")
    public RespPageBean getEmpSalaryByPage(@RequestParam(defaultValue = "1") Long page, @RequestParam(defaultValue = "10") Long size, String empName){
        return adjustSalaryService.getEmpSalaryByPage(page,size,empName);
    }

    @PostMapping("/")
    public RespBean addEmpSalary(@RequestBody Employee employee){
        if (adjustSalaryService.addEmpSalary(employee)>0){
            return RespBean.success("添加成功!");
        }
        return RespBean.fail("添加失败!");
    }

    @DeleteMapping("/")
    public RespBean delEmpSalary(@RequestParam("eid") Integer eid){
        if (adjustSalaryService.delEmpSalary(eid)>0){
            return RespBean.success("删除成功!");
        }
        return RespBean.fail("删除失败!");
    }
}
