package com.example.springboot.dao;


import com.example.springboot.entity.Params;
import com.example.springboot.entity.Type;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface TypeDao extends Mapper<Type> {


    List<Type> findBySearch(@Param("params") Params params);
}
