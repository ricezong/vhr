package com.gz.vhr.mapper;

import com.gz.vhr.bean.HrRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@Repository
public interface HrRoleMapper extends BaseMapper<HrRole> {

    Integer addRole(@Param("hrid") Integer hrid,@Param("rids") Integer[] rids);
}
