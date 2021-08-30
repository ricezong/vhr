package com.gz.vhr.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 职位等级
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("joblevel")
public class JobLevel implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 职称名称
     */
    @TableField("name")
    private String name;

    @TableField("titleLevel")
    private String titleLevel;

    @TableField("createDate")
    private LocalDateTime createDate;

    @TableField("enabled")
    private Boolean enabled;


}
