package com.example.springboot.service;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.springboot.dao.BookDao;
import com.example.springboot.dao.TypeDao;
import com.example.springboot.entity.Book;
import com.example.springboot.entity.Params;
import com.example.springboot.entity.Type;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class BookService {
    @Resource
    private BookDao bookDao;
    @Resource
    private TypeDao typeDao;
    public PageInfo<Book> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Book> list=bookDao.findBySearch(params);
        if(CollectionUtil.isEmpty(list)){
            return PageInfo.of(new ArrayList<>());
        }
        for(Book book:list){
            if(ObjectUtil.isNotEmpty(book.getTypeId())){
                Type type=typeDao.selectByPrimaryKey(book.getTypeId());
                if(ObjectUtil.isNotEmpty(type.getName())){
                    book.setTypeName(type.getName());
                }
            }


        }
        return PageInfo.of(list);
    }

    public void add(Book book) {

        bookDao.insertSelective(book);
    }


    public void update(Book book) {
        bookDao.updateByPrimaryKeySelective(book);
    }
    public void delete(Integer id) {
        bookDao.deleteByPrimaryKey(id);
    }
}
