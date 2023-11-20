package com.example.springboot.controller;


import com.example.springboot.common.Result;
import com.example.springboot.entity.Audit;
import com.example.springboot.entity.Params;
import com.example.springboot.service.AuditService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@CrossOrigin
@RestController
@RequestMapping("/audit")
public class AuditController {
    @Resource
    private AuditService auditService;

    @PostMapping
    public Result save(@RequestBody Audit audit){
        if(audit.getId()==null){
            auditService.add(audit);
        }else{
            auditService.update(audit);
        }
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        auditService.delete(id);
        return Result.success();
    }

    @GetMapping("/search")
    public Result findSearch(Params params){
        PageInfo<Audit> info=auditService.findBySearch(params);
        return Result.success(info);
    }


}
