package com.gz.vhr.controller.emp.adv;

import com.gz.vhr.bean.EmployeeTrain;
import com.gz.vhr.bean.RespBean;
import com.gz.vhr.bean.RespPageBean;
import com.gz.vhr.service.EmployeeTrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName TrainController
 * @Description 培训资料
 * @Author 孔明灯
 * @Data 2022/2/18 9:46
 * @Version 1.0
 */
@RestController
@RequestMapping("/emp/adv/train")
public class TrainController {

    @Autowired
    EmployeeTrainService employeeTrainService;

    @GetMapping("/")
    public RespPageBean getAllTrains(@RequestParam(defaultValue = "1") Long page, @RequestParam(defaultValue = "10") Long size, String empName){
        return employeeTrainService.getAllTrains(page,size,empName);
    }

    @DeleteMapping("/")
    public RespBean delTrainsByIds(@RequestParam("ids") Integer[] ids){
        if (employeeTrainService.delTrainsByIds(ids)==ids.length){
            return RespBean.success("删除成功");
        }
        return RespBean.fail("删除失败");
    }
}
