package com.example.springboot.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springboot.entity.Admin;
import com.example.springboot.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenUtils {
    private static AdminService staticAdminService;
    private static final Logger log= LoggerFactory.getLogger(JwtTokenUtils.class);
    @Resource
    private AdminService adminService;

    @PostConstruct
    public void setUserService(){
        staticAdminService=adminService;
    }

    public static String genToken(String userId, String sign){
        return JWT.create().withAudience(userId)
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))
                .sign(Algorithm.HMAC256(sign));
    }

    public static Admin getCurrentUser(){
        String token=null;
        try{
            HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            token =request.getHeader("token");
            if(StrUtil.isBlank(token)){
                token=request.getParameter("token");
            }
            if(StrUtil.isBlank(token)){
                log.error("获取当前登录的token失败，token：{}",token);
                return null;
            }
            String adminId=JWT.decode(token).getAudience().get(0);

        return staticAdminService.findById(Integer.valueOf((adminId)));
        }catch (Exception e){
            log.error("获取当前登录的管理员信息失败，token：{}",token,e);
            return null;
        }
    }
}
