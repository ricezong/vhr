package com.gz.vhr.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gz.vhr.bean.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gz.vhr.bean.EmployeeEc;
import com.gz.vhr.bean.RespPageBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {

    List<Employee> getEmployeeByPage(@Param("page") Long page,
                                     @Param("size") Long size,
                                     @Param("emp")Employee employee,
                                     @Param("dateScope")String[] dateScope);

    Long getTotal(@Param("emp")Employee employee, @Param("dateScope")String[] dateScope);

    List<Employee> getEmployeeByPageWithSalary(@Param("page") Long page, @Param("size") Long size);

    Integer updateEmpSalById(@Param("eid") Integer eid,@Param("sid") Integer sid);

    Integer maxWorkID();

    Long getCount(@Param("depID") Integer depID,@Param("empName")String empName);

    List<Employee> getAllEmpSalByDepId(@Param("page") Long page,
                                       @Param("size") Long size,
                                       @Param("depID")Integer depID,
                                       @Param("empName")String empName);

    Integer getEmpTestScoreById(@Param("id")Integer id);

    List<Employee> getALlEmpByDepID(Integer depID);

    List<EmployeeEc> getEmpEcPointByEmpID(Integer empID);
}
