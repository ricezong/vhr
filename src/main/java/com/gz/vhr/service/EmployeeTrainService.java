package com.gz.vhr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gz.vhr.bean.Employee;
import com.gz.vhr.bean.EmployeeTrain;
import com.gz.vhr.bean.RespPageBean;
import com.gz.vhr.mapper.EmployeeTrainMapper;
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
public class EmployeeTrainService extends ServiceImpl<EmployeeTrainMapper, EmployeeTrain> implements IService<EmployeeTrain> {

    @Autowired
    EmployeeTrainMapper employeeTrainMapper;

    public RespPageBean getAllTrains(Long page, Long size, String empName) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> allTrainVo = employeeTrainMapper.getAllTrains(page, size,empName);
        Long total = employeeTrainMapper.getCount(empName);
        return new RespPageBean(total, allTrainVo);
    }

    public Integer delTrainsByIds(Integer[] ids) {
        List list=new ArrayList();
        for (int i=0;i<ids.length;i++){
            list.add(ids[i]);
        }
        return employeeTrainMapper.deleteBatchIds(list);
    }

    public RespPageBean getEmpTrainByPage(Long page, Long size, String empName) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> employeeTrainVo = employeeTrainMapper.getEmpTrainByPage(page, size,empName);
        Long total = employeeTrainMapper.getTotal(empName);
        return new RespPageBean(total, employeeTrainVo);
    }

    public Integer addEmpTrain(Employee employee) {
        employee.getEmployeeTrain().setEid(employee.getId());
        return employeeTrainMapper.insert(employee.getEmployeeTrain());
    }

    public Integer delEmpTrain(Integer eid){
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("eid",eid);
        return employeeTrainMapper.deleteByMap(columnMap);
    }
}
