package com.gz.vhr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gz.vhr.bean.Employee;
import com.gz.vhr.bean.EmployeeRemove;
import com.gz.vhr.bean.RespPageBean;
import com.gz.vhr.mapper.EmployeeRemoveMapper;
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
public class EmployeeRemoveService extends ServiceImpl<EmployeeRemoveMapper, EmployeeRemove> implements IService<EmployeeRemove> {

    @Autowired
    EmployeeRemoveMapper employeeRemoveMapper;

    public RespPageBean getAllMvs(Long page, Long size, String empName) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> allTrainVo = employeeRemoveMapper.getAllMvs(page, size,empName);
        Long total = employeeRemoveMapper.getCount(empName);
        return new RespPageBean(total, allTrainVo);
    }

    public Integer delMvsByIds(Integer[] ids) {
        List list=new ArrayList();
        for (int i=0;i<ids.length;i++){
            list.add(ids[i]);
        }
        return employeeRemoveMapper.deleteBatchIds(list);
    }

    public RespPageBean getEmpMvByPage(Long page, Long size, String empName) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> employeeMvVo = employeeRemoveMapper.getEmpMvByPage(page, size,empName);
        Long total = employeeRemoveMapper.getTotal(empName);
        return new RespPageBean(total, employeeMvVo);
    }

    public Integer addEmpMv(Employee employee) {
        employee.getEmployeeRemove().setEid(employee.getId());
        return employeeRemoveMapper.insert(employee.getEmployeeRemove());
    }

    public Integer delEmpMv(Integer eid){
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("eid",eid);
        return employeeRemoveMapper.deleteByMap(columnMap);
    }
}
