package com.gz.vhr.controller.per;


import com.gz.vhr.bean.Employee;
import com.gz.vhr.bean.RespBean;
import com.gz.vhr.bean.RespPageBean;
import com.gz.vhr.service.EmployeeTrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  员工培训
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@RestController
@RequestMapping("/personnel/train")
public class EmployeeTrainController {

    @Autowired
    EmployeeTrainService employeeTrainService;

    @GetMapping("/")
    public RespPageBean getEmpTrainByPage(@RequestParam(defaultValue = "1") Long page, @RequestParam(defaultValue = "10") Long size, String empName){
        return employeeTrainService.getEmpTrainByPage(page,size,empName);
    }

    @PostMapping("/")
    public RespBean addEmpTrain(@RequestBody Employee employee){
        if (employeeTrainService.addEmpTrain(employee)>0){
            return RespBean.success("添加成功!");
        }
        return RespBean.fail("添加失败!");
    }

    @DeleteMapping("/")
    public RespBean delEmpTrain(@RequestParam("eid") Integer eid){
        if (employeeTrainService.delEmpTrain(eid)>0){
            return RespBean.success("删除成功!");
        }
        return RespBean.fail("删除失败!");
    }
}

