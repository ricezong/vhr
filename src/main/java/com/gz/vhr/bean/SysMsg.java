package com.gz.vhr.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统消息
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sysmsg")
public class SysMsg implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 消息id
     */
    @TableField("mid")
    private Integer mid;

    /**
     * 0表示群发消息
     */
    @TableField("type")
    private Integer type;

    /**
     * 这条消息是给谁的
     */
    @TableField("hrid")
    private Integer hrid;

    /**
     * 0 未读 1 已读
     */
    @TableField("state")
    private Integer state;


}
