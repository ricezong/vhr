package com.gz.vhr.controller.emp;

import com.gz.vhr.bean.*;
import com.gz.vhr.service.*;
import com.gz.vhr.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  员工基本信息
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@RestController
@RequestMapping("/employee/basic")
public class EmpBasicController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    NationService nationService;
    @Autowired
    PoliticsStatusService politicsStatusService;
    @Autowired
    JobLevelService jobLevelService;
    @Autowired
    PositionService positionService;
    @Autowired
    DepartmentService departmentService;

    /**
     *分页查询
     * 根据员工姓名搜索员工
     * @param page 页码
     * @param size 每页记录数
     * @return
     */
    @GetMapping("/")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Long page,
                                          @RequestParam(defaultValue = "10") Long size,
                                          Employee employee,
                                          String[] dateScope){
        return employeeService.getEmployeeByPage(page,size,employee,dateScope);
    }

    /**
     * 添加员工
     * @param employee
     * @return
     */
    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee){
        if (employeeService.addEmp(employee)>0){
            return RespBean.success("添加成功");
        }
        return RespBean.fail("添加失败");
    }

    /**
     * 根据id删除员工
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public RespBean delEmpByEid(@PathVariable Integer id){
        if (employeeService.delEmpByEid(id)>0){
            return RespBean.success("删除成功");
        }
        return RespBean.fail("删除失败");
    }

    /**
     * 修改员工信息
     * @param employee
     * @return
     */
    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee){
        if (employeeService.updateEmp(employee)>0){
            return RespBean.success("修改成功");
        }
        return RespBean.success("修改成功");
    }

    /**
     * 查询所有国籍
     * @return
     */
    @GetMapping("/nations")
    public List<Nation> getAllNations(){
        return nationService.getAllNations();
    }

    /**
     * 查询所有政治地位
     * @return
     */
    @GetMapping("/politicsstatus")
    public List<PoliticsStatus> getAllPoliticsStatus(){
        return politicsStatusService.getAllPoliticsStatus();
    }

    /**
     * 查询所有职称
     * @return
     */
    @GetMapping("/joblevels")
    public List<JobLevel> getAllJobLevel(){
        return jobLevelService.getAllJobLevel();
    }

    /**
     * 查询所有职位
     * @return
     */
    @GetMapping("/positions")
    public List<Position> getAllPositions(){
        return positionService.getAllPositions();
    }

    /**
     * 返回最大工号
     * @return
     */
    @GetMapping("/maxWorkID")
    public RespBean maxWorkID(){
        return RespBean.build().setStatus(200).setObj(String.format("%08d",employeeService.maxWorkID()+1));
    }

    /**
     * 查询所有部门
     * @return
     */
    @GetMapping("/deps")
    public List<Department> getAllDeps(){
        return departmentService.getAllDepartments();
    }

    /**
     * 导出数据
     * @return
     */
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData(){
        List<Employee> list  = (List<Employee>) employeeService.getEmployeeByPage(null, null, new Employee(),null).getData();
        return POIUtils.employee2Excel(list);
    }

    /**
     * 文件上传并写入数据库
     * @param file
     * @return
     */
    @PostMapping("/import")
    public RespBean importData(MultipartFile file){
        List<Employee> list=POIUtils.excel2Employee(
                file,
                nationService.getAllNations(),
                politicsStatusService.getAllPoliticsStatus(),
                departmentService.getAllDepartmentsWithOutChildren(),
                positionService.getAllPositions(),
                jobLevelService.getAllJobLevel());
        if (employeeService.addEmps(list)==list.size()){
            return RespBean.success("上传成功");
        }
        return RespBean.fail("上传失败");
    }
}

