package com.example.blog.model;


import lombok.Data;

@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String photo;
    private String createtime;
    private String updatetime;
}
