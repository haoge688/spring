package com.example.springboot.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Params;
import com.example.springboot.entity.Type;
import com.example.springboot.exception.CustomException;
import com.example.springboot.service.TypeService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/type")
public class TypeController {
    @Resource
    private TypeService typeService;

    @GetMapping
    public Result findAll(){
        return Result.success(typeService.findAll());
    }

    @PostMapping
    public Result save(@RequestBody Type type){
        if(type.getId()==null){
            typeService.add(type);
        }else{
            typeService.update(type);
        }
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        typeService.delete(id);
        return Result.success();
    }

    @GetMapping("/search")
    public Result findSearch(Params params){
        PageInfo<Type> info=typeService.findBySearch(params);
        return Result.success(info);
    }
    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<Type> list){
        for(Type type:list){
            typeService.delete(type.getId());
        }
        return Result.success();
    }
    @GetMapping("/export")
    public Result export(HttpServletResponse response) throws IOException {
        //一行一行的组装数据塞到excel里面
        List<Type> all=typeService.findAll();
        if(CollectionUtil.isEmpty(all)){
            throw new CustomException("未找到数据");
        }
        List<Map<String,Object>> list=new ArrayList<>(all.size());
        for (Type type:all){
            Map<String,Object> row=new HashMap<>();
            row.put("分类名称",type.getName());
            row.put("分类描述",type.getDescription());
            list.add(row);
        }
        ExcelWriter wr= ExcelUtil.getWriter(true);
        wr.write(list,true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=type.xlsx");
        ServletOutputStream out =response.getOutputStream();
        wr.flush(out,true);
        wr.close();
        IoUtil.close(System.out);

        return Result.success();
    }
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException{
        List<Type> infoList =ExcelUtil.getReader(file.getInputStream()).readAll(Type.class);
        if(!CollectionUtil.isEmpty(infoList)){
            for(Type type:infoList){
                try{
                    typeService.add(type);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        return Result.success();
    }

}
