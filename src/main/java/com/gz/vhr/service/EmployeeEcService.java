package com.gz.vhr.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gz.vhr.bean.Employee;
import com.gz.vhr.bean.EmployeeEc;
import com.gz.vhr.bean.RespPageBean;
import com.gz.vhr.mapper.EmployeeEcMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gz.vhr.mapper.EmployeeMapper;
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
public class EmployeeEcService extends ServiceImpl<EmployeeEcMapper, EmployeeEc> implements IService<EmployeeEc> {

    @Autowired
    EmployeeEcMapper employeeEcMapper;
    @Autowired
    EmployeeMapper employeeMapper;

    public RespPageBean getAllEc(Long page, Long size, String empName) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> employeeSalaryVo = employeeEcMapper.getAllEc(page, size,empName);
        Long total = employeeEcMapper.getCount(empName);
        return new RespPageBean(total, employeeSalaryVo);
    }

    public Integer delEcsByIds(Integer[] ids) {
        List list=new ArrayList();
        for (int i=0;i<ids.length;i++){
            list.add(ids[i]);
        }
        return employeeEcMapper.deleteBatchIds(list);
    }

    public RespPageBean getEmployeeEcByPage(Long page, Long size,String empName) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> employeeEcVo = employeeEcMapper.getEmployeeEcByPage(page, size,empName);
        Long total = employeeEcMapper.getTotal(empName);
        return new RespPageBean(total, employeeEcVo);
    }

    public Integer addEmpEc(Employee employee) {
        employee.getEmployeeEc().setEid(employee.getId());
        switch (employee.getEmployeeEc().getEcType()){
            case 0:
                employee.setTestScore(employeeMapper.getEmpTestScoreById(employee.getId())+employee.getEmployeeEc().getEcPoint());
                break;
            case 1:
                employee.setTestScore(employeeMapper.getEmpTestScoreById(employee.getId())-employee.getEmployeeEc().getEcPoint());
                break;
        }
        if (employeeMapper.updateById(employee)>0){
            return employeeEcMapper.insert(employee.getEmployeeEc());
        }
        return 0;
    }

    public Integer delEmpEc(Integer eid){
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("eid",eid);
        Employee employee=new Employee();
        employee.setTestScore(0);
        employee.setId(eid);
        if (employeeMapper.updateById(employee)>0){
            return employeeEcMapper.deleteByMap(columnMap);
        }
        return 0;
    }
}
