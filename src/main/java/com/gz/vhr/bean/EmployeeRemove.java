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
 * 部门调动信息
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("employeeremove")
public class EmployeeRemove implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("eid")
    private Integer eid;

    /**
     * 调动后部门
     */
    @TableField("afterDepId")
    private Integer afterDepId;

    /**
     * 调动后职位
     */
    @TableField("afterJobId")
    private Integer afterJobId;

    /**
     * 调动日期
     */
    @TableField("removeDate")
    private LocalDate removeDate;

    /**
     * 调动原因
     */
    @TableField("reason")
    private String reason;

    @TableField("remark")
    private String remark;


}
