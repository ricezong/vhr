package com.gz.vhr.controller.emp.adv;

import com.gz.vhr.bean.RespBean;
import com.gz.vhr.bean.RespPageBean;
import com.gz.vhr.service.EmployeeRemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName RemoveController
 * @Description 调动资料
 * @Author 孔明灯
 * @Data 2022/2/18 9:45
 * @Version 1.0
 */
@RestController
@RequestMapping("/emp/adv/remove")
public class RemoveController {

    @Autowired
    EmployeeRemoveService employeeRemoveService;

    @GetMapping("/")
    public RespPageBean getAllMvs(@RequestParam(defaultValue = "1") Long page, @RequestParam(defaultValue = "10") Long size, String empName){
        return employeeRemoveService.getAllMvs(page,size,empName);
    }


    @DeleteMapping("/")
    public RespBean delMvsByIds(@RequestParam("ids") Integer[] ids){
        if (employeeRemoveService.delMvsByIds(ids)==ids.length){
            return RespBean.success("删除成功");
        }
        return RespBean.fail("删除失败");
    }
}
