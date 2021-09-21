package com.gz.vhr.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gz.vhr.bean.Employee;
import com.gz.vhr.bean.RespPageBean;
import com.gz.vhr.mapper.EmployeeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@Service
public class EmployeeService extends ServiceImpl<EmployeeMapper, Employee> implements IService<Employee> {

    @Autowired
    EmployeeMapper employeeMapper;

    public RespPageBean getEmployeeByPage(Long pageNum, Long size) {
        if (pageNum != null && size != null) {
            pageNum = (pageNum - 1) * size;
        }
        List<Employee> employeeVo = employeeMapper.getEmployeeByPage(pageNum, size);
        Long total = employeeMapper.getTotal(null,null);
        return new RespPageBean(total, employeeVo);
    }

    public RespPageBean getEmployeeByPageWithSalary(Long pageNum, Long size) {
        if (pageNum != null && size != null) {
            pageNum = (pageNum - 1) * size;
        }
        List<Employee> employeeVo = employeeMapper.getEmployeeByPageWithSalary(pageNum, size);
        Long total = employeeMapper.getTotal(null,null);
        return new RespPageBean(total, employeeVo);
    }

    public Integer updateEmpSalById(Integer eid, Integer sid) {
        return employeeMapper.updateEmpSalById(eid,sid);
    }
}
