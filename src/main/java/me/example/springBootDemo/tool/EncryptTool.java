package me.example.springBootDemo.tool;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

@Component
public class EncryptTool {


    public String encrypt(String source, String salt){
        SimpleHash hash = new SimpleHash("md5",source, ByteSource.Util.bytes(salt),2);
        return  hash.toHex();
    }
}
