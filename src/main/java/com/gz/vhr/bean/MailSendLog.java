package com.gz.vhr.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 邮件发送日志信息
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mail_send_log")
public class MailSendLog implements Serializable {


    @TableField("msgId")
    private String msgId;

    @TableField("empId")
    private Integer empId;

    /**
     * 0发送中，1发送成功，2发送失败
     */
    @TableField("status")
    private Integer status;

    @TableField("routeKey")
    private String routeKey;

    @TableField("exchange")
    private String exchange;

    /**
     * 重试次数
     */
    @TableField("count")
    private Integer count;

    /**
     * 第一次重试时间
     */
    @TableField("tryTime")
    private LocalDate tryTime;

    @TableField("createTime")
    private LocalDate createTime;

    @TableField("updateTime")
    private LocalDate updateTime;


}
