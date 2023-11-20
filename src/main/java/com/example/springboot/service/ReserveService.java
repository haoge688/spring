package com.example.springboot.service;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.springboot.dao.AdminDao;
import com.example.springboot.dao.HotelDao;
import com.example.springboot.dao.ReserveDao;
import com.example.springboot.entity.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class ReserveService {
    @Resource
    private ReserveDao reserveDao;
    @Resource
    private HotelDao hotelDao;
    @Resource
    private AdminDao adminDao;

    public PageInfo<Reserve> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Reserve> list=reserveDao.findBySearch(params);
        if(CollectionUtil.isEmpty(list)){
            return PageInfo.of(new ArrayList<>());
        }
        for(Reserve reserve:list){
            if(ObjectUtil.isNotEmpty(reserve.getHotelId())){
                Hotel hotel=hotelDao.selectByPrimaryKey(reserve.getHotelId());
                if(ObjectUtil.isNotEmpty(hotel)){
                    reserve.setHotelName(hotel.getName());
                }
            }
            if(ObjectUtil.isNotEmpty(reserve.getUserId())){
                Admin admin=adminDao.selectByPrimaryKey(reserve.getUserId());
                if(ObjectUtil.isNotEmpty(admin)){
                    reserve.setUserName(admin.getName());
                }
            }

        }
        return PageInfo.of(list);
    }

    public void add(Reserve reserve) {
        reserveDao.insertSelective(reserve);
    }


    public void update(Reserve reserve) {
        reserveDao.updateByPrimaryKeySelective(reserve);
    }
    public void delete(Integer id) {
        reserveDao.deleteByPrimaryKey(id);
    }
}
