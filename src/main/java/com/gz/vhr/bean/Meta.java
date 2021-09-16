package com.gz.vhr.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @ClassName Meta
 * @Description TODO
 * @Author 孔明灯
 * @Data 2021/8/17 15:47
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Meta {
    //是否存活
    private Boolean keepAlive;
    //是否需要验证
    private Boolean requireAuth;
}
