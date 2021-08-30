package com.gz.vhr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gz.vhr.bean.Hr;
import com.gz.vhr.mapper.HrMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@Service
public class HrService extends ServiceImpl<HrMapper, Hr> implements UserDetailsService, IService<Hr> {

    @Resource
    HrMapper hrMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Hr> wrapper=new QueryWrapper<>();
        wrapper.eq("username",username);
        Hr hr = hrMapper.selectOne(wrapper);
        if (hr == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return hr;
    }
}