package com.gz.vhr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gz.vhr.bean.Hr;
import com.gz.vhr.mapper.HrMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gz.vhr.mapper.HrRoleMapper;
import com.gz.vhr.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    HrMapper hrMapper;
    @Autowired
    HrRoleMapper hrRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Hr> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        Hr hr = hrMapper.selectOne(queryWrapper);
        if (hr == null) {
            throw new UsernameNotFoundException("用户名不存在!");
        }
        hr.setRoles(hrMapper.getHrRolesById(hr.getId()));
        return hr;
    }

    public Hr getCurrentHr() {
        return hrMapper.getCurrentHr(HrUtils.getCurrentHr().getId());
    }

    public List<Hr> getAllHrs(String keywords) {
        return hrMapper.getAllHrs(HrUtils.getCurrentHr().getId(),keywords);
    }

    public Integer updateHr(Hr hr) {
        UpdateWrapper<Hr> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",hr.getId()).set("enabled",hr.getEnabled());
        return hrMapper.update(null,updateWrapper);
    }

    public Integer updateHrInfo(Hr hr) {
        UpdateWrapper<Hr> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",hr.getId())
                     .set("name",hr.getName())
                     .set("phone",hr.getPhone())
                     .set("telephone",hr.getTelephone())
                     .set("address",hr.getAddress())
                     .set("remark",hr.getRemark());
        return hrMapper.update(null,updateWrapper);
    }

    @Transactional
    public boolean updateHrRoles(Integer hrid, Integer[] rids) {
        Map<String,Object> map = new HashMap<>();
        map.put("hrid",hrid);
        hrRoleMapper.deleteByMap(map);
        return hrRoleMapper.addRole(hrid,rids)==rids.length;
    }

    public Integer deleteHrById(Integer id) {
        return hrMapper.deleteById(id);
    }

    public List<Map<String, Object>> getAllHrsExceptCurrentHr() {
        QueryWrapper<Hr> queryWrapper=new QueryWrapper<>();
        queryWrapper.ne("id",HrUtils.getCurrentHr().getId());
        return hrMapper.selectMaps(queryWrapper);
    }

    //TODO
    public boolean updateHrPwd(String oldpass, String pass, Integer hrid) {
        Hr hr = hrMapper.selectById(hrid);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(oldpass, hr.getPassword())) {
            UpdateWrapper<Hr> wrapper=new UpdateWrapper();
            wrapper.eq("id",hrid).set("password",hr.setPassword(encoder.encode(pass)));
            if (hrMapper.update(null,wrapper)>0) {
                return true;
            }
        }
        return false;
    }

    /*public Integer updateUserFace(String url, Integer id) {
        Hr hr=new Hr();
        hr.setUserFace(url);
        hr.setId(id);
        return hrMapper.updateById(hr);
    }*/
}
