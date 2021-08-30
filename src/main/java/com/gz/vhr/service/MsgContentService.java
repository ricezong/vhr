package com.gz.vhr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gz.vhr.bean.MsgContent;
import com.gz.vhr.mapper.MsgContentMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@Service
public class MsgContentService extends ServiceImpl<MsgContentMapper, MsgContent> implements IService<MsgContent> {

}
