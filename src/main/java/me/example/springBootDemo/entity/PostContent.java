package me.example.springBootDemo.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 帖子内容（一个元组表示一个帖子楼层）
 * </p>
 *
 * @author hxy
 * @since 2019-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PostContent implements Serializable {

private static final long serialVersionUID=1L;

    @TableId
    private Long postId;
    @TableId
    private Integer floorId;

    /**
     * 帖子内容
     */
    private String content;

    private LocalDateTime createDatetime;

    private String authodId;

    private String authodUsername;


}
