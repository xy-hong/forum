package me.example.springBootDemo.controller;

import me.example.springBootDemo.custom.Result;
import me.example.springBootDemo.custom.Role;
import me.example.springBootDemo.custom.Tip;
import me.example.springBootDemo.entity.Post;
import me.example.springBootDemo.mapper.PostMapper;
import me.example.springBootDemo.service.impl.PostService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/postList/{pageNo}")
    public Result postList(@PathVariable("pageNo") int pageNo){
        List<Post> postList = postService.postList(pageNo,10);
        return new Result(3,"测试成功",postList);
    }

    @GetMapping("/postList")
    public Result postList(){
        List<Post> postList = postService.postList();
        return new Result(3,"测试成功",postList);
    }

    @PutMapping("/post")
    @RequiresRoles(value = {"ADMIN","USER"}, logical = Logical.OR)
    public Result addPost(Post post){

        if (postService.addPost(post)>0){
            return Result.successResult(Tip.UPDATE_SUCCESS);
        }else{
            return Result.failResult(Tip.UPDATE_FAIL);
        }
    }

    @DeleteMapping("/post")
    @RequiresRoles("ADMIN")
    public Result deletePost(String id){

        if(postService.deletePost(id)>0){
            return Result.successResult(Tip.DELETE_SUCCESS);
        }else{
            return Result.failResult(Tip.DELETE_FAIL);
        }
    }

    // todo 怎么验证是否有权限，是否是帖子主人或者管理员
    @PostMapping("/post")
    public Result updatePost(Post post){

        if (postService.update(post)>0){
            return Result.successResult(Tip.UPDATE_SUCCESS);
        }else{
            return Result.failResult(Tip.UPDATE_FAIL);
        }

    }


}
