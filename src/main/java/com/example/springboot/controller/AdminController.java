package com.example.springboot.controller;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Params;
import com.example.springboot.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;
    //前端接口
    @PostMapping("/login")
    public Result login(@RequestBody Admin admin){
        Admin loginUser=adminService.login(admin);
        return Result.success(loginUser);
    }
    @PostMapping("/register")
    public Result register(@RequestBody Admin admin){
        adminService.add(admin);
        return Result.success();
    }


    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        adminService.delete(id);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody Admin admin){
        if(admin.getId()==null){
            adminService.add(admin);
        }else{
            adminService.update(admin);
        }

        return Result.success();
    }
    @GetMapping
    public Result findAll(){
        List<Admin> list=adminService.findAll();
        return Result.success(list);

    }
    @GetMapping("/search")
    public Result findSearch(Params params){
        PageInfo<Admin> info=adminService.findBySearch(params);
        return Result.success(info);
    }


}
