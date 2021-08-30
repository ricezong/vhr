package com.gz.vhr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gz.vhr.bean.Menu;
import com.gz.vhr.mapper.MenuMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class MenuService extends ServiceImpl<MenuMapper, Menu> implements IService<Menu> {

    public List<Menu> getMenusByHrId(Integer id) {
        return null;
    }
}
