package com.gz.vhr.mapper;

import com.gz.vhr.bean.Employee;
import com.gz.vhr.bean.EmployeeRemove;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
public interface EmployeeRemoveMapper extends BaseMapper<EmployeeRemove> {

    List<Employee> getEmpMvByPage(@Param("page") Long page, @Param("size") Long size, @Param("empName")String empName);

    Long getTotal(@Param("empName")String empName);

    List<Employee> getAllMvs(@Param("page") Long page, @Param("size") Long size, @Param("empName")String empName);

    Long getCount(@Param("empName")String empName);
}
