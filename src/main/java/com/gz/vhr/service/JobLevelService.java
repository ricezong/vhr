package com.gz.vhr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gz.vhr.bean.JobLevel;
import com.gz.vhr.mapper.JobLevelMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
public class JobLevelService extends ServiceImpl<JobLevelMapper, JobLevel> implements IService<JobLevel> {


    @Autowired
    JobLevelMapper jobLevelMapper;

    public List<JobLevel> getAllJobLevel() {
        QueryWrapper<JobLevel> queryWrapper=new QueryWrapper<JobLevel>();
        return jobLevelMapper.selectList(queryWrapper);
    }

    public Integer addJobLevel(JobLevel jobLevel) {
        jobLevel.setCreateDate(new Date());
        jobLevel.setEnabled(true);
        return jobLevelMapper.insert(jobLevel);
    }

    public Integer updateJobLevel(JobLevel jobLevel) {
        QueryWrapper<JobLevel> queryWrapper=new QueryWrapper<JobLevel>();
        queryWrapper.eq("id",jobLevel.getId());
        return jobLevelMapper.update(jobLevel,queryWrapper);
    }

    public Integer deleteJobLevelById(Integer id) {
        return jobLevelMapper.deleteById(id);
    }

    public Integer deleteJobLevelsByIds(@Param("ids") Integer[] ids) {
        List list=new ArrayList();
        for (int i=0;i<ids.length;i++){
            list.add(ids[i]);
        }
        return jobLevelMapper.deleteBatchIds(list);
    }
}
