package me.example.springBootDemo.service.impl;

import me.example.springBootDemo.entity.PostContent;
import me.example.springBootDemo.mapper.PostContentMapper;
import me.example.springBootDemo.service.IPostContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 帖子内容（一个元组表示一个帖子楼层） 服务实现类
 * </p>
 *
 * @author hxy
 * @since 2019-05-26
 */
@Service
public class PostContentServiceImpl extends ServiceImpl<PostContentMapper, PostContent> implements IPostContentService {

}
