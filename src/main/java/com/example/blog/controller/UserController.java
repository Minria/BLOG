package com.example.blog.controller;


import com.example.blog.config.FinalVar;
import com.example.blog.dao.UserMapper;
import com.example.blog.model.User;
import com.example.blog.util.BLOGException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/login")
    @SneakyThrows
    @ResponseBody
    public Object login(User user, HttpServletRequest request){
        User loginUser=userMapper.selectByNameAndPassword(user.getUsername(), user.getPassword());
        if(loginUser==null){
            throw new BLOGException("账号或者密码错误");
        }
        HttpSession session=request.getSession();
        session.setAttribute(FinalVar.USER_SESSION,loginUser);
        return loginUser;
    }

    @SneakyThrows
    @RequestMapping("reg")
    @ResponseBody
    public Object register(User user, @RequestPart MultipartFile file) throws BLOGException {
        User findUser=userMapper.selectByName(user.getUsername());
        if(findUser!=null){
            throw new BLOGException("账号已经存在");
        }
        int result = userMapper.add(user);
        if(result==0){
            throw new BLOGException("注册失败");
        }
        String path= Objects.requireNonNull(Objects.requireNonNull(ClassUtils.getDefaultClassLoader()).getResource("static")).getPath();
        path+="/upload/";
        String fileType=file.getOriginalFilename();
        assert fileType != null;
        fileType=fileType.substring(fileType.lastIndexOf("."));
        String fileName= UUID.randomUUID() +fileType;
        file.transferTo(new File(path+fileName));
        return user;
    }
}
