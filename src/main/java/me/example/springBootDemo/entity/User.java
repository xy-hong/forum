package me.example.springBootDemo.entity;

import lombok.Data;

@Data
public class User {
    private String userId;
    private String password;
    private String salt;
    private byte role;
}
