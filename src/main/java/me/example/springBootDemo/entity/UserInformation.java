package me.example.springBootDemo.entity;

import java.time.LocalDate;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 此表保存用户的个人信息
 * </p>
 *
 * @author hxy
 * @since 2019-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserInformation implements Serializable {

private static final long serialVersionUID=1L;

    @TableId("user_id")
    private String userId;

    private String username;

    /**
     * 头像，保存头像图片路径
     */
    private String head;

    private LocalDate registerDate;


}
