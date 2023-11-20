package com.example.springboot.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.springboot.common.JwtTokenUtils;
import com.example.springboot.dao.AuditDao;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Audit;
import com.example.springboot.entity.Params;
import com.example.springboot.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AuditService {
    @Resource
    private AuditDao auditDao;


    public void add(Audit audit) {
        auditDao.insertSelective(audit);
    }
    public void update(Audit audit) {
        auditDao.updateByPrimaryKeySelective(audit);
    }
    public PageInfo<Audit> findBySearch(Params params) {
        Admin user=JwtTokenUtils.getCurrentUser();
        if(ObjectUtil.isNull(user)){
            throw new CustomException("用户未登录，请登录");
        }
        if("ROLE_STUDENT".equals(user.getRole())){
            params.setUserId(user.getId());
        }
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Audit> list=auditDao.findBySearch(params);


        return PageInfo.of(list);
    }
    public void delete(Integer id) {
        auditDao.deleteByPrimaryKey(id);
    }

}
