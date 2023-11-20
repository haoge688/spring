package com.example.springboot.controller;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Hotel;
import com.example.springboot.entity.Params;
import com.example.springboot.service.HotelService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;


@CrossOrigin
@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Resource
    private HotelService hotelService;
    @GetMapping("/search")
    public Result findSearch(Params params){
        PageInfo<Hotel> info=hotelService.findBySearch(params);
        return Result.success(info);
    }
    @PostMapping
    public Result save(@RequestBody Hotel hotel){
        if(hotel.getId()==null){
            hotelService.add(hotel);
        }else{
            hotelService.update(hotel);
        }
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        hotelService.delete(id);
        return Result.success();
    }
}
