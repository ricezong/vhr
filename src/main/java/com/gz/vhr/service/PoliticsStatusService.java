package com.gz.vhr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gz.vhr.bean.PoliticsStatus;
import com.gz.vhr.mapper.PoliticsStatusMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  政治地位
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@Service
public class PoliticsStatusService extends ServiceImpl<PoliticsStatusMapper, PoliticsStatus> implements IService<PoliticsStatus> {

    @Autowired
    PoliticsStatusMapper politicsStatusMapper;

    public List<PoliticsStatus> getAllPoliticsStatus() {
        QueryWrapper<PoliticsStatus> queryWrapper=new QueryWrapper<>();
        return politicsStatusMapper.selectList(queryWrapper);
    }
}
