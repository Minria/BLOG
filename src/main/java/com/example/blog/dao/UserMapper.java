package com.example.blog.dao;

import com.example.blog.model.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {

    int add(User user);
    User selectByNameAndPassword(String username,String password);
    User selectById(int id);
    User selectByName(String username);
}
