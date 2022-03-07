package com.gz.vhr.controller.system.basic;

import com.gz.vhr.bean.Position;
import com.gz.vhr.bean.RespBean;
import com.gz.vhr.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  职位管理
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {

    @Autowired
    PositionService positionService;

    @GetMapping("/")
    public List<Position> getAllPositions(){
        return positionService.getAllPositions();
    }

    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position){
        if (positionService.addPosition(position)>0){
            return RespBean.success("添加成功");
        }
        return RespBean.fail("添加失败");
    }

    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position){
        if (positionService.updatePosition(position)>0){
            return RespBean.success("更新成功");
        }
        return RespBean.fail("更新失败");
    }

    @DeleteMapping("/{id}")
    public RespBean delPositionById(@PathVariable Integer id){
        if (positionService.delPositionById(id)>0){
            return RespBean.success("删除成功");
        }
        return RespBean.fail("删除失败");
    }

    @DeleteMapping("/")
    public RespBean delPositionsByIds(Integer[] ids){
        if (positionService.delPositionsByIds(ids)==ids.length){
            return RespBean.success("删除成功");
        }
        return RespBean.fail("删除失败");
    }
}

