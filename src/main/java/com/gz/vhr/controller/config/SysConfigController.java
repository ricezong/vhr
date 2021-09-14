package com.gz.vhr.controller.config;

import com.gz.vhr.bean.Menu;
import com.gz.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName SysConfigController
 * @Description TODO
 * @Author 孔明灯
 * @Data 2021/8/17 15:53
 * @Version 1.0
 */
@RestController
@RequestMapping("/system/config")
public class SysConfigController {

    @Autowired
    MenuService menuService;

    @GetMapping("/menu")
    public List<Menu> getMenusByHrId(){
        return menuService.getMenusByHrId();
    }
}
