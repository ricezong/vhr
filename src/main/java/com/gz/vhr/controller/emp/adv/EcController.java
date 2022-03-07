package com.gz.vhr.controller.emp.adv;

import com.gz.vhr.bean.Employee;
import com.gz.vhr.bean.EmployeeEc;
import com.gz.vhr.bean.RespBean;
import com.gz.vhr.bean.RespPageBean;
import com.gz.vhr.service.EmployeeEcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName EcController
 * @Description 奖惩资料
 * @Author 孔明灯
 * @Data 2022/2/17 15:55
 * @Version 1.0
 */
@RestController
@RequestMapping("/emp/adv/ec")
public class EcController {

    @Autowired
    EmployeeEcService employeeEcService;

    @GetMapping("/")
    public RespPageBean getAllEc(@RequestParam(defaultValue = "1") Long page, @RequestParam(defaultValue = "10") Long size, String empName){
        return employeeEcService.getAllEc(page,size,empName);
    }


    @DeleteMapping("/")
    public RespBean delEcsByIds(@RequestParam("ids") Integer[] ids){
        if (employeeEcService.delEcsByIds(ids)==ids.length){
            return RespBean.success("删除成功");
        }
        return RespBean.fail("删除失败");
    }
}
