package com.gz.vhr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gz.vhr.bean.Hr;
import com.gz.vhr.bean.RespBean;
import com.gz.vhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName SecurityConfig
 * @Description TODO
 * @Author 孔明灯
 * @Data 2021/7/30 19:19
 * @Version 1.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    HrService hrService;

    /**
     * 密码加密
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder managerBuilder) throws Exception{
        managerBuilder.userDetailsService(hrService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/doLogin")
                .loginPage("/Login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter writer = httpServletResponse.getWriter();
                        Hr hr = (Hr) authentication.getPrincipal();
                        //不生成密码
                        hr.setPassword(null);
                        RespBean success = RespBean.success("登录成功", hr);
                        String s = new ObjectMapper().writeValueAsString(success);
                        writer.write(s);
                        writer.flush();
                        writer.close();
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter writer = httpServletResponse.getWriter();
                        RespBean respBean= RespBean.fail("登录失败");
                        if (e instanceof LockedException){
                            respBean.setMsg("账户被锁定，请联系管理员");
                        }else if (e instanceof CredentialsExpiredException){
                            respBean.setMsg("密码过期，请联系管理员");
                        }else if (e instanceof AccountExpiredException){
                            respBean.setMsg("账户过期，请联系管理员");
                        }else if (e instanceof DisabledException){
                            respBean.setMsg("账户被禁用，请联系管路员");
                        }else if (e instanceof BadCredentialsException){
                            respBean.setMsg("用户名或密码输入错误，请重新输入");
                        }
                        writer.write(new ObjectMapper().writeValueAsString(respBean));
                        writer.flush();
                        writer.close();
                    }
                })
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter writer = httpServletResponse.getWriter();
                        writer.write(new ObjectMapper().writeValueAsString(RespBean.success("退出成功")));
                        writer.flush();
                        writer.close();
                    }
                })
                .permitAll()
                .and()
                .csrf()
                .disable();
    }
}
