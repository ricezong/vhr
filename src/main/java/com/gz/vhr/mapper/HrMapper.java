package com.gz.vhr.mapper;

import com.gz.vhr.bean.Hr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gz.vhr.bean.Role;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;


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
public interface HrMapper extends BaseMapper<Hr> {

    List<Role> getHrRolesById(Integer id);

    Hr loadUserByUsername(String username);

    List<Hr> getAllHrs(@Param("hrid") Integer hrid, @Param("keywords") String keywords);
}
