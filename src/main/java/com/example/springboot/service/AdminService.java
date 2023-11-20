package com.example.springboot.service;

import com.example.springboot.common.JwtTokenUtils;
import com.example.springboot.dao.AdminDao;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Params;
import com.example.springboot.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminService {
    @Resource
    private AdminDao adminDao;
    public List<Admin> findAll(){
        return adminDao.selectAll();
    }
    public PageInfo<Admin> findBySearch(Params params){
        //开启分页查询
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Admin> list=adminDao.findBySearch(params);
        return PageInfo.of(list);
    }
    public void add(Admin admin) {
        if(admin.getName()==null||admin.getName()==""){
            throw new CustomException("用户名不能为空");
        }
        Admin user=adminDao.findByName(admin.getName());
        if(user!=null){
            throw new CustomException("该用户名已存在,请勿重新添加");
        }
        if(admin.getPassword()==null){
            admin.setPassword("123456");
        }
        adminDao.insertSelective(admin);
    }

    public void update(Admin admin) {
        adminDao.updateByPrimaryKeySelective(admin);
    }

    public void delete(Integer id) {
        adminDao.deleteByPrimaryKey(id);
    }
    public Admin login(Admin admin) {
        if(admin.getName()==null ||admin.getName()==""){
            throw new CustomException("请输入姓名");
        }
        if(admin.getPassword()==null ||"".equals(admin.getPassword()) ){
            throw new CustomException("请输入密码");
        }
        Admin user=adminDao.findByNameAndPassword(admin.getName(),admin.getPassword());
        if(user==null){
            throw new CustomException("用户名或密码输入错误");
        }
        String token=JwtTokenUtils.genToken(user.getId().toString(),user.getPassword());
        user.setToken(token);
        return user;
    }
    public Admin findById(Integer id) {
        return adminDao.selectByPrimaryKey(id);
    }
}
