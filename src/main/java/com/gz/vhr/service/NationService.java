package com.gz.vhr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gz.vhr.bean.JobLevel;
import com.gz.vhr.bean.Nation;
import com.gz.vhr.mapper.NationMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  国籍
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@Service
public class NationService extends ServiceImpl<NationMapper, Nation> implements IService<Nation> {

    @Autowired
    NationMapper nationMapper;

    public List<Nation> getAllNations() {
        QueryWrapper<Nation> queryWrapper=new QueryWrapper<>();
        return nationMapper.selectList(queryWrapper);
    }
}
