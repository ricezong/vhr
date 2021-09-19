package com.gz.vhr.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName ChatMessage
 * @Description 聊天信息封装
 * @Author 孔明灯
 * @Data 2021/9/19 16:55
 * @Version 1.0
 */
@Component
@Data
public class ChatMsg {
    private String from;
    private String to;
    private String content;
    private Date date;
    private String fromNickname;
}
