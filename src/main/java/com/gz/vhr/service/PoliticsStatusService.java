package com.gz.vhr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gz.vhr.bean.PoliticsStatus;
import com.gz.vhr.mapper.PoliticsStatusMapper;
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
public class PoliticsStatusService extends ServiceImpl<PoliticsStatusMapper, PoliticsStatus> implements IService<PoliticsStatus> {

}
