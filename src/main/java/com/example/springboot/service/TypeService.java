package com.example.springboot.service;

import com.example.springboot.dao.TypeDao;
import com.example.springboot.entity.Params;
import com.example.springboot.entity.Type;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TypeService {
    @Resource
    private TypeDao typeDao;
    public List<Type> findAll(){
        return typeDao.selectAll();
    }

    public void add(Type type) {
        typeDao.insertSelective(type);
    }
    public void update(Type type) {
        typeDao.updateByPrimaryKeySelective(type);
    }
    public PageInfo<Type> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Type> list=typeDao.findBySearch(params);
        return PageInfo.of(list);
    }
    public void delete(Integer id) {
        typeDao.deleteByPrimaryKey(id);
    }



}
