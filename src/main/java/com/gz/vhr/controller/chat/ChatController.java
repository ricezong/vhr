package com.gz.vhr.controller.chat;

import com.gz.vhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ChatController
 * @Description 聊天
 * @Author 孔明灯
 * @Data 2021/9/19 15:21
 * @Version 1.0
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    HrService hrService;

    @GetMapping("/hrs")
    public List<Map<String, Object>> getAllHrs(){
        return hrService.getAllHrsExceptCurrentHr();
    }
}
