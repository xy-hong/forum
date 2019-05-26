package me.example.springBootDemo.custom;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class Role {
   public static byte ADMIN = 0;
    public static byte USER = 1;
    public static byte BLACKLIST = 2;

}

class Test{
    public static void main(String[] args) throws IllegalAccessException {
        Class<Role> roleClass = Role.class;
        Field[] fields = roleClass.getDeclaredFields();


        for(Field field: fields){
           String fieldName = field.getName();
           System.out.println(fieldName);
          System.out.println(field.get(null));
        }
    }
}


