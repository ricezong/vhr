package com.gz.vhr.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @ClassName Meta
 * @Description TODO
 * @Author 孔明灯
 * @Data 2021/8/17 15:47
 * @Version 1.0
 */
@Data
@Component
public class Meta {
    private Boolean keepAlive;
    private Boolean requireAuth;

}
