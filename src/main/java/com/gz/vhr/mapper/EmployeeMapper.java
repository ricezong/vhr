package com.gz.vhr.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gz.vhr.bean.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

    List<Employee> getEmployeeByPage(@Param("pageNum") Long pageNum, @Param("size") Long size);

    Long getTotal(@Param("emp") Employee employee,@Param("beginDateScope") Date[] beginDateScope);

    List<Employee> getEmployeeByPageWithSalary(@Param("pageNum") Long pageNum, @Param("size") Long size);

    Integer updateEmpSalById(@Param("eid") Integer eid,@Param("sid") Integer sid);
}
