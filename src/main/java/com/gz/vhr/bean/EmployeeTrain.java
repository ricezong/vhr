package com.gz.vhr.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 员工培训信息
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("employeetrain")
public class EmployeeTrain implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 员工编号
     */
    @TableField("eid")
    private Integer eid;

    /**
     * 培训日期
     */
    @TableField("trainDate")
    private LocalDate trainDate;

    /**
     * 培训内容
     */
    @TableField("trainContent")
    private String trainContent;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;


}
