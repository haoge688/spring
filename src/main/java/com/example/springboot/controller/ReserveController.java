package com.example.springboot.controller;

import cn.hutool.core.date.DateUtil;
import com.example.springboot.common.Result;
import com.example.springboot.dao.HotelDao;
import com.example.springboot.entity.Hotel;
import com.example.springboot.entity.Params;
import com.example.springboot.entity.Reserve;
import com.example.springboot.service.ReserveService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@CrossOrigin
@RestController
@RequestMapping("/reserve")
public class ReserveController {
    @Resource
    private ReserveService reserveService;
    @Resource
    private HotelDao hotelDao;
    @GetMapping("/search")
    public Result findSearch(Params params){
        PageInfo<Reserve> info=reserveService.findBySearch(params);
        return Result.success(info);
    }
    @PostMapping
    public Result save(@RequestBody Reserve reserve){
        Hotel hotel=hotelDao.selectByPrimaryKey(reserve.getHotelId());
        if(hotel.getNum()==0){
            return Result.error("酒店该房间已预定完");
        }
        reserve.setTime(DateUtil.now());
        reserveService.add(reserve);
        hotel.setNum(hotel.getNum()-1);
        hotelDao.updateByPrimaryKeySelective(hotel);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        reserveService.delete(id);
        return Result.success();
    }
}
