package me.example.springBootDemo.mapper;

import me.example.springBootDemo.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 帖子概要信息（即不包含帖子内容） Mapper 接口
 * </p>
 *
 * @author hxy
 * @since 2019-05-22
 */
public interface PostMapper extends BaseMapper<Post> {

}
