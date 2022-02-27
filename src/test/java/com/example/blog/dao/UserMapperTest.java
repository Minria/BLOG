package com.example.blog.dao;

import com.example.blog.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    void selectByNameAndPassword() {
        String name="wfm";
        String password="123";
        User user=userMapper.selectByNameAndPassword(name,password);
        System.out.println(user);
    }

    @Test
    void add() {
        System.out.println(userMapper);
        User user=new User();
        user.setUsername("wfm");
        user.setPassword("123");
        user.setPhoto("/test");
        int res = userMapper.add(user);
        System.out.println(res);
    }
}