package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Book;
import com.example.springboot.entity.Params;
import com.example.springboot.service.BookService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {
    @Resource
    private BookService bookService;
    @GetMapping("/search")
    public Result findSearch(Params params){
        PageInfo<Book> info=bookService.findBySearch(params);
        return Result.success(info);
    }
    @PostMapping
    public Result save(@RequestBody Book book){
        if(book.getId()==null){
            bookService.add(book);
        }else{
            bookService.update(book);
        }

        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        bookService.delete(id);
        return Result.success();
    }
}
