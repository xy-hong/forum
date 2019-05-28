package me.example.springBootDemo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {
    @TableId
    private String userId;
    private String password;
    private String salt;
    private byte role;
}
