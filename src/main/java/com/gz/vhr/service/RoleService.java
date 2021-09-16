package com.gz.vhr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gz.vhr.bean.Menu;
import com.gz.vhr.bean.Role;
import com.gz.vhr.mapper.RoleMapper;
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
public class RoleService extends ServiceImpl<RoleMapper, Role> implements IService<Role> {

    @Autowired
    RoleMapper roleMapper;

    public List<Role> getAllRoles() {
        QueryWrapper<Role> queryWrapper=new QueryWrapper<Role>();
        return roleMapper.selectList(queryWrapper);
    }

    public Integer addRole(Role role) {
        if (!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        return roleMapper.insert(role);
    }

    public Integer deleteRoleById(Integer rid) {
        return roleMapper.deleteById(rid);
    }
}
