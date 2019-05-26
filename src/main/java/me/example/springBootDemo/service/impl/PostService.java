package me.example.springBootDemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.example.springBootDemo.entity.Post;
import me.example.springBootDemo.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostMapper postMapper;


    public List<Post> postList(){
        return postMapper.selectList(null);
    }

    public List<Post> postList(int pageNo, int pageSize){
        IPage<Post> postPage = new Page<Post>(pageNo, pageSize);
        postPage =  postMapper.selectPage(postPage,null);
        return postPage.getRecords();
    }

    public int addPost(Post post){
        return  postMapper.insert(post);
    }

    public int deletePost(String id){
        return postMapper.deleteById(id);
    }

    public int update(Post post){
        return  postMapper.updateById(post);
    }
}
