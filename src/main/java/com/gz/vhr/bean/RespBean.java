package com.gz.vhr.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * @ClassName RespBean
 * @Description 返回响应信息
 * @Author 孔明灯
 * @Data 2021/7/30 19:44
 * @Version 1.0
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RespBean {
    private Integer status;
    private String msg;
    private Object obj;

    public static RespBean success(String msg){
        return new RespBean(200,msg,null);
    }

    public static RespBean success(String msg, Object obj){
        return new RespBean(200,msg,obj);
    }

    public static RespBean fail(String msg){
        return new RespBean(500,msg,null);
    }

    public static RespBean fail(String msg, Object obj){
        return new RespBean(500,msg,obj);
    }
}
