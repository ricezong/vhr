package com.gz.vhr.controller.system;

import com.gz.vhr.bean.Hr;
import com.gz.vhr.bean.RespBean;
import com.gz.vhr.bean.Role;
import com.gz.vhr.service.HrService;
import com.gz.vhr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  操作員
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@RestController
@RequestMapping("/system/hr")
public class HrController {

    @Autowired
    HrService hrService;
    @Autowired
    RoleService roleService;

    @GetMapping("/")
    public List<Hr> getAllHrs(String keywords){
        return hrService.getAllHrs(keywords);
    }

    @PutMapping("/")
    public RespBean updateHr(@RequestBody Hr hr){
        if(hrService.updateHr(hr)>0){
            return RespBean.success("更新成功");
        }
        return RespBean.fail("更新失败");
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

    @PutMapping("/roles")
    public RespBean updateHrRoles(Integer hrid,Integer[] rids){
        if (hrService.updateHrRoles(hrid,rids)){
            return RespBean.success("更新成功");
        }
        return RespBean.fail("更新失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteHrById(@PathVariable Integer id){
        if (hrService.deleteHrById(id)>0){
            return RespBean.success("删除成功");
        }
        return RespBean.fail("删除失败");
    }
}

