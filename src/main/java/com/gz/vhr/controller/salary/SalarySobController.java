package com.gz.vhr.controller.salary;


import com.gz.vhr.bean.RespBean;
import com.gz.vhr.bean.Salary;
import com.gz.vhr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  工资账套设置
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@RestController
@RequestMapping("/salary/sob")
public class SalarySobController {

    @Autowired
    SalaryService salaryService;

    @GetMapping("/")
    public List<Salary> getAllSalaries(){
        return salaryService.getAllSalaries();
    }

    @PostMapping("/")
    public RespBean addSalary(@RequestBody Salary salary){
        if (salaryService.addSalary(salary)>0){
            return RespBean.success("添加成功");
        }
        return RespBean.fail("添加失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteSalaryById(@PathVariable Integer id){
        if (salaryService.deleteSalaryById(id)>0){
            return RespBean.success("删除成功");
        }
        return RespBean.fail("删除失败");
    }

    @PutMapping("/")
    public RespBean updateSalaryById(@RequestBody Salary salary){
        if (salaryService.updateSalaryById(salary)>0){
            return RespBean.success("更新成功");
        }
        return RespBean.fail("更新失败");
    }
}

