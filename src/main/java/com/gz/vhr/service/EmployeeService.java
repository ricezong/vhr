package com.gz.vhr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gz.vhr.bean.Employee;
import com.gz.vhr.bean.EmployeeEc;
import com.gz.vhr.bean.RespPageBean;
import com.gz.vhr.mapper.AdjustSalaryMapper;
import com.gz.vhr.mapper.EmployeeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @Autowired
    AdjustSalaryMapper adjustSalaryMapper;

    DateTimeFormatter yearFormat = DateTimeFormatter.ofPattern("yyyy");
    DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("MM");
    DecimalFormat decimalFormat = new DecimalFormat("##.00");

    public RespPageBean getEmployeeByPage(Long page, Long size,Employee employee,String[] dateScope) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> employeeVo = employeeMapper.getEmployeeByPage(page, size,employee,dateScope);
        Long total = employeeMapper.getTotal(employee,dateScope);
        return new RespPageBean(total, employeeVo);
    }

    public RespPageBean getEmployeeByPageWithSalary(Long page, Long size) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> employeeVo = employeeMapper.getEmployeeByPageWithSalary(page, size);
        Long total = employeeMapper.getTotal(null,null);
        return new RespPageBean(total, employeeVo);
    }

    public Integer updateEmpSalById(Integer eid, Integer sid) {
        return employeeMapper.updateEmpSalById(eid,sid);
    }

    public Integer addEmp(Employee employee) {
        LocalDate beginContract=employee.getBeginContract();
        LocalDate endContract=employee.getEndContract();
        double year = (Double.parseDouble(yearFormat.format(endContract)) - Double.parseDouble(yearFormat.format(beginContract))) * 12;
        double month = (Double.parseDouble(monthFormat.format(endContract)) - Double.parseDouble(monthFormat.format(beginContract)));
        employee.setContractTerm(Double.parseDouble(decimalFormat.format((year+month)/12)));
        return employeeMapper.insert(employee);
    }

    public Integer maxWorkID() {
        return employeeMapper.maxWorkID();
    }

    public Integer delEmpByEid(Integer id) {
        return employeeMapper.deleteById(id);
    }

    public Integer updateEmp(Employee employee) {
        LocalDate beginContract=employee.getBeginContract();
        LocalDate endContract=employee.getEndContract();
        double year = (Double.parseDouble(yearFormat.format(endContract)) - Double.parseDouble(yearFormat.format(beginContract))) * 12;
        double month = (Double.parseDouble(monthFormat.format(endContract)) - Double.parseDouble(monthFormat.format(beginContract)));
        employee.setContractTerm(Double.parseDouble(decimalFormat.format((year+month)/12)));
        return employeeMapper.updateById(employee);
    }

    public Integer addEmps(List<Employee> list) {
        int result = 0;
        for (Employee employee : list) {
            if (employeeMapper.insert(employee)>0){
                result++;
            }
        }
        return result;
    }

    public RespPageBean getAllEmpSalByDepId(Long page, Long size, Integer depID,String workDate,String empName) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> allAdjusts = adjustSalaryMapper.getAllUpToDateAdjusts(workDate);
        List<Employee> employeeVo = employeeMapper.getAllEmpSalByDepId(page, size, depID,empName);
        for (Employee emp : employeeVo) {
            if (emp.getTestScore() != 0) {
                emp.getSalary().setBonus(emp.getTestScore() * 100 + emp.getSalary().getBonus());
            }
            if (allAdjusts!=null){
                for (Employee employee : allAdjusts) {
                    if (emp.getId() == employee.getAdjustSalary().getEid()) {
                        emp.getSalary().setBasicSalary(employee.getAdjustSalary().getAfterSalary());
                    }
                }
            }
            if (emp.getSalary()!=null){
                emp.getSalary().setAllSalary(emp.getSalary().getBasicSalary() + emp.getSalary().getBonus() + emp.getSalary().getLunchSalary() + emp.getSalary().getTrafficSalary());
            }
        }
        Long total = employeeMapper.getCount(depID,empName);
        return new RespPageBean(total, employeeVo);
    }

    public List<Employee> getALlEmpByDepID(Integer depID) {
        return employeeMapper.getALlEmpByDepID(depID);
    }

    public List<EmployeeEc> getEmpEcPointByEmpID(Integer empID) {
        return employeeMapper.getEmpEcPointByEmpID(empID);
    }
}
