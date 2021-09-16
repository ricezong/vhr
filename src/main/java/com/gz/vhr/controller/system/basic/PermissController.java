package com.gz.vhr.controller.system.basic;

import com.gz.vhr.bean.Menu;
import com.gz.vhr.bean.RespBean;
import com.gz.vhr.bean.Role;
import com.gz.vhr.service.MenuService;
import com.gz.vhr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName PermissController
 * @Description TODO
 * @Author 孔明灯
 * @Data 2021/9/14 20:14
 * @Version 1.0
 */
@RestController
@RequestMapping("/system/basic/permiss")
public class PermissController {
    @Autowired
    RoleService roleService;
    @Autowired
    MenuService menuService;

    @GetMapping("/")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

    @GetMapping("/menus")
    public List<Menu> getAllMenus(){
        return menuService.getAllMenus();
    }

    @GetMapping("/mids/{rid}")
    public List<Integer> getMidsByRid(@PathVariable Integer rid){
        return menuService.getMidsByRid(rid);
    }

    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid,Integer[] mids){
        if (menuService.updateMenuRole(rid,mids)>0){
            return RespBean.success("更新成功");
        }
        return RespBean.fail("更新失败");
    }

    @PostMapping("/role")
    public RespBean addRole(@RequestBody Role role){
        if (roleService.addRole(role)>0){
            return RespBean.success("添加成功");
        }
        return RespBean.fail("添加失败");
    }

    @DeleteMapping("/role/{rid}")
    public RespBean deleteRoleById(@PathVariable Integer rid){
        if (roleService.deleteRoleById(rid)>0){
            return RespBean.success("删除成功");
        }
        return RespBean.success("删除失败");
    }
}