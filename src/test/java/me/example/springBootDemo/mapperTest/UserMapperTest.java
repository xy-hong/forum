package me.example.springBootDemo.mapperTest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.example.springBootDemo.entity.User;
import me.example.springBootDemo.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        //List<User> userList = userMapper.selectList(null);
        Page<User> page = new Page<User>();
        page.setCurrent(2);
        page.setSize(2);
        //page.setTotal(2);
        IPage<User> userIPage = userMapper.selectPage(page,null);
        List<User> userList = userIPage.getRecords();
        for (User user: userList){
            System.out.println(user);
        }
    }

}