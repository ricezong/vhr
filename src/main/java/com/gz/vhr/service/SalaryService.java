package com.gz.vhr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gz.vhr.bean.Salary;
import com.gz.vhr.mapper.SalaryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
public class SalaryService extends ServiceImpl<SalaryMapper, Salary> implements IService<Salary> {

    @Autowired
    SalaryMapper salaryMapper;

    public List<Salary> getAllSalaries() {
        return salaryMapper.selectList(null);
    }

    public Integer addSalary(Salary salary) {
        salary.setCreateDate(new Date());
        return salaryMapper.insert(salary);
    }

    public Integer deleteSalaryById(Integer id) {
        return salaryMapper.deleteById(id);
    }

    public Integer updateSalaryById(Salary salary) {
        return salaryMapper.updateById(salary);
    }
}
