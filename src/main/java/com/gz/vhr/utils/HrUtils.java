package com.gz.vhr.utils;

import com.gz.vhr.bean.Hr;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @ClassName HrUtils
 * @Description TODO
 * @Author 孔明灯
 * @Data 2021/9/16 8:40
 * @Version 1.0
 */
@Component
public class HrUtils {

    /**
     * 获取当前用户
     */
    public static Hr getCurrentHr(){
        return (Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
