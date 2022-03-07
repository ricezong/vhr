package com.gz.vhr.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 员工信息
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("employee")
@ToString
public class Employee implements Serializable {


    /**
     * 员工编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 员工姓名
     */
    @TableField("name")
    private String name;

    /**
     * 性别
     */
    @TableField("gender")
    private String gender;

    /**
     * 出生日期
     */
    @TableField("birthday")
    private LocalDate birthday;

    /**
     * 身份证号
     */
    @TableField("idCard")
    private String idCard;

    /**
     * 婚姻状况
     */
    @TableField("wedlock")
    private String wedlock;

    /**
     * 民族
     */
    @TableField("nationId")
    private Integer nationId;

    /**
     * 籍贯
     */
    @TableField("nativePlace")
    private String nativePlace;

    /**
     * 政治面貌
     */
    @TableField("politicId")
    private Integer politicId;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 电话号码
     */
    @TableField("phone")
    private String phone;

    /**
     * 联系地址
     */
    @TableField("address")
    private String address;

    /**
     * 所属部门
     */
    @TableField("departmentId")
    private Integer departmentId;

    /**
     * 职称ID
     */
    @TableField("jobLevelId")
    private Integer jobLevelId;

    /**
     * 职位ID
     */
    @TableField("posId")
    private Integer posId;

    /**
     * 聘用形式
     */
    @TableField("engageForm")
    private String engageForm;

    /**
     * 最高学历
     */
    @TableField("tiptopDegree")
    private String tiptopDegree;

    /**
     * 所属专业
     */
    @TableField("specialty")
    private String specialty;

    /**
     * 毕业院校
     */
    @TableField("school")
    private String school;

    /**
     * 入职日期
     */
    @TableField("beginDate")
    private LocalDate beginDate;

    /**
     * 在职状态
     */
    @TableField("workState")
    private String workState;

    /**
     * 工号
     */
    @TableField("workID")
    private String workID;

    /**
     * 合同期限
     */
    @TableField("contractTerm")
    private Double contractTerm;

    /**
     * 转正日期
     */
    @TableField("conversionTime")
    private LocalDate conversionTime;

    /**
     * 离职日期
     */
    @TableField("notWorkDate")
    private LocalDate notWorkDate;

    /**
     * 合同起始日期
     */
    @TableField("beginContract")
    private LocalDate beginContract;

    /**
     * 合同终止日期
     */
    @TableField("endContract")
    private LocalDate endContract;

    /**
     * 工龄
     */
    @TableField("workAge")
    private Integer workAge;

    /**
     * 考评分
     */
    @TableField("testScore")
    private Integer testScore;

    //国籍
    @TableField(exist = false)
    private Nation nation;

    //政治面貌
    @TableField(exist = false)
    private PoliticsStatus politicsStatus;

    //部门信息
    @TableField(exist = false)
    private Department department;

    //职称等级
    @TableField(exist = false)
    private JobLevel jobLevel;

    //职位
    @TableField(exist = false)
    private Position position;

    //薪资
    @TableField(exist = false)
    private Salary salary;

    //奖惩
    @TableField(exist = false)
    private EmployeeEc employeeEc;

    //培训
    @TableField(exist = false)
    private EmployeeTrain employeeTrain;

    //调薪
    @TableField(exist = false)
    private AdjustSalary adjustSalary;

    //调动
    @TableField(exist = false)
    private EmployeeRemove employeeRemove;


    public void setBirthday(Date birthday) {
        this.birthday = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void setConversionTime(Date conversionTime) {
        this.conversionTime = conversionTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void setBeginContract(Date beginContract) {
        this.beginContract = beginContract.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void setEndContract(Date endContract) {
        this.endContract = endContract.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
