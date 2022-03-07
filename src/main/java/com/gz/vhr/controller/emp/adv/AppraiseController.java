package com.gz.vhr.controller.emp.adv;

import com.gz.vhr.service.AppraiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AppraiseController
 * @Description 考评资料
 * @Author 孔明灯
 * @Data 2022/2/18 9:44
 * @Version 1.0
 */
@RestController
@RequestMapping("/emp/adv/appraise")
public class AppraiseController {

    @Autowired
    AppraiseService appraiseService;
}
