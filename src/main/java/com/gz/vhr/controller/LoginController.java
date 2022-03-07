package com.gz.vhr.controller;

import com.gz.vhr.bean.RespBean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LoginController
 * @Description 用户登录
 * @Author 孔明灯
 * @Data 2021/8/1 12:55
 * @Version 1.0
 */
@RestController
public class LoginController {

    @GetMapping("/login")
    //@CrossOrigin("*") 解决跨域
    public RespBean login(){
        return RespBean.fail("尚未登录,请登录！");
    }
}
