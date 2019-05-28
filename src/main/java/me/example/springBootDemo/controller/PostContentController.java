package me.example.springBootDemo.controller;


import me.example.springBootDemo.custom.Result;
import me.example.springBootDemo.entity.PostContent;
import me.example.springBootDemo.service.IPostContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 帖子内容（一个元组表示一个帖子楼层） 前端控制器
 * </p>
 *
 * @author hxy
 * @since 2019-05-26
 */
@RestController
@RequestMapping("/postContent")
public class PostContentController {

    @Autowired
    public IPostContentService postContentService;

    @PostMapping
    public Result getPostContents(){
        List<PostContent> postContents = postContentService.list();
        return new Result(3, postContents);
    }
}

