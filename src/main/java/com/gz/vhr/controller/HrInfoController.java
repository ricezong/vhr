package com.gz.vhr.controller;

import com.gz.vhr.bean.Hr;
import com.gz.vhr.bean.RespBean;
import com.gz.vhr.config.FastDFSUtils;
import com.gz.vhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 *  个人中心
 * </p>
 *
 * @author zong
 * @since 2021-07-31
 */
@RestController
public class HrInfoController {

    @Autowired
    HrService hrService;

    /*@Value("${fastdfs.nginx.host}")
    String nginxHost;*/

    @GetMapping("/hr/info")
    public Hr getCurrentHr(Authentication authentication) {
        return hrService.getCurrentHr();
        //return ((Hr) authentication.getPrincipal());
    }

    @PutMapping("/hr/info")
    public RespBean updateHrInfo(@RequestBody Hr hr) {
        if (hrService.updateHrInfo(hr) == 1) {
            return RespBean.success("更新成功!");
        }
        return RespBean.fail("更新失败!");
    }

    @PutMapping("/hr/pass")
    public RespBean updateHrPwd(@RequestBody Map<String, Object> info) {
        String oldpass = (String) info.get("oldpass");
        String pass = (String) info.get("pass");
        Integer hrid = (Integer) info.get("hrid");
        if (hrService.updateHrPwd(oldpass, pass, hrid)) {
            return RespBean.success("更新成功!");
        }
        return RespBean.fail("更新失败!");
    }

    /*@PostMapping("/hr/userface")
    public RespBean updateHrUserFace(MultipartFile file, Integer id, Authentication authentication) {
        String fileId = FastDFSUtils.upload(file);
        String url = nginxHost + fileId;
        if (hrService.updateUserFace(url, id) == 1) {
            Hr hr = (Hr) authentication.getPrincipal();
            hr.setUserFace(url);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(hr, authentication.getCredentials(), authentication.getAuthorities()));
            return RespBean.success("更新成功!", url);
        }
        return RespBean.fail("更新失败!");
    }*/

}