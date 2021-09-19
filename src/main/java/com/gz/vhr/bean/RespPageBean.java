package com.gz.vhr.bean;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName RespPageBean
 * @Description 分页实体类
 * @Author 孔明灯
 * @Data 2021/9/16 19:18
 * @Version 1.0
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RespPageBean {
    //总记录数
    private long total;
    //分页数据
    private List<?> data;
}
