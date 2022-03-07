package com.gz.vhr.mapper;

import com.gz.vhr.bean.AdjustSalary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gz.vhr.bean.Employee;
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
public interface AdjustSalaryMapper extends BaseMapper<AdjustSalary> {

    List<Employee> getEmpSalaryByPage(@Param("page") Long page, @Param("size") Long size, @Param("empName")String empName);

    Long getTotal(@Param("empName")String empName);

    List<Employee> getAllAdjusts(@Param("page") Long page, @Param("size") Long size, @Param("empName")String empName);

    Long getCount(@Param("empName")String empName);

    //获取最新调薪数据（已去重）
    List<Employee> getAllUpToDateAdjusts(@Param("workMonth") String workMonth);
}
