package com.gz.vhr.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gz.vhr.bean.Position;
import com.gz.vhr.bean.RespBean;
import com.gz.vhr.mapper.PositionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javafx.geometry.Pos;
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
public class PositionService extends ServiceImpl<PositionMapper, Position> implements IService<Position> {

    @Autowired
    PositionMapper positionMapper;

    public List<Position> getAllPositions(){
        QueryWrapper<Position> queryWrapper=new QueryWrapper<Position>();
        return positionMapper.selectList(queryWrapper);
    }

    public Integer addPosition(Position position) {
        position.setEnabled(true);
        position.setCreateDate(new Date());
        return positionMapper.insert(position);
    }

    public Integer updatePosition(Position position) {
        QueryWrapper<Position> wrapper=new QueryWrapper<Position>();
        wrapper.eq("id",position.getId());
        return positionMapper.update(position,wrapper);
    }

    public Integer delPositionById(Integer id) {
        return positionMapper.deleteById(id);
    }

    public Integer delPositionsByIds(@Param("ids") Integer[] ids) {
        List list=new ArrayList();
        for (int i=0;i<ids.length;i++){
            list.add(ids[i]);
        }
        return positionMapper.deleteBatchIds(list);
    }
}
