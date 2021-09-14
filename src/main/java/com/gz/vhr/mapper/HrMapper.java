package com.gz.vhr.mapper;

import com.gz.vhr.bean.Hr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gz.vhr.bean.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
public interface HrMapper extends BaseMapper<Hr> {

    List<Role> getHrRolesById(Integer id);

    Hr loadUserByUsername(String username);
}
