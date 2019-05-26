package me.example.springBootDemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 帖子概要信息（即不包含帖子内容）
 * </p>
 *
 * @author hxy
 * @since 2019-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Post implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "post_id", type = IdType.AUTO)
    private Long postId;

    private String title;

    private String authorId;

    private LocalDateTime createDatetime;

    /**
     * 封面图片路径
     */
    private String cover;

    /**
     * 主题id，可根据id从表theme获取
     */
    private Integer theme;

    /**
     * 帖子访问量
     */
    private Integer traffic;

    /**
     * 是否为精华置顶帖子，0-否  1-是
     */
    private Boolean top;


}
