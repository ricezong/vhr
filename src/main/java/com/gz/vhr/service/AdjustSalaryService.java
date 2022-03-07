package com.gz.vhr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gz.vhr.bean.AdjustSalary;
import com.gz.vhr.bean.Employee;
import com.gz.vhr.bean.RespPageBean;
import com.gz.vhr.mapper.AdjustSalaryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@Service
public class AdjustSalaryService extends ServiceImpl<AdjustSalaryMapper, AdjustSalary> implements IService<AdjustSalary> {

    @Autowired
    AdjustSalaryMapper adjustSalaryMapper;

    public RespPageBean getEmpSalaryByPage(Long page, Long size, String empName) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> employeeSalaryVo = adjustSalaryMapper.getEmpSalaryByPage(page, size,empName);
        Long total = adjustSalaryMapper.getTotal(empName);
        return new RespPageBean(total, employeeSalaryVo);
    }

    public Integer addEmpSalary(Employee employee) {
        employee.getAdjustSalary().setEid(employee.getId());
        return adjustSalaryMapper.insert(employee.getAdjustSalary());
    }

    public Integer delEmpSalary(Integer eid){
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("eid",eid);
        return adjustSalaryMapper.deleteByMap(columnMap);
    }

    public RespPageBean getAllAdjusts(Long page, Long size, String empName) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> adJustVo = adjustSalaryMapper.getAllAdjusts(page, size,empName);
        Long total = adjustSalaryMapper.getCount(empName);
        return new RespPageBean(total, adJustVo);
    }

    public int delAdjustsByIds(Integer[] ids) {
        List list=new ArrayList();
        for (int i=0;i<ids.length;i++){
            list.add(ids[i]);
        }
        return adjustSalaryMapper.deleteBatchIds(list);
    }
}
