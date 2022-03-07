package com.gz.vhr.controller.emp.adv;

import com.gz.vhr.bean.RespBean;
import com.gz.vhr.bean.RespPageBean;
import com.gz.vhr.service.AdjustSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName AdjustController
 * @Description TODO
 * @Author 孔明灯
 * @Data 2022/2/20 10:24
 * @Version 1.0
 */
@RestController
@RequestMapping("/emp/adv/adjust")
public class AdjustController {

    @Autowired
    AdjustSalaryService adjustSalaryService;

    @GetMapping("/")
    public RespPageBean getAllAdjusts(@RequestParam(defaultValue = "1") Long page, @RequestParam(defaultValue = "10") Long size, String empName){
        return adjustSalaryService.getAllAdjusts(page,size,empName);
    }

    @DeleteMapping("/")
    public RespBean delAdjustsByIds(@RequestParam("ids") Integer[] ids){
        if (adjustSalaryService.delAdjustsByIds(ids)==ids.length){
            return RespBean.success("删除成功");
        }
        return RespBean.fail("删除失败");
    }
}
