package me.example.springBootDemo.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 主题
 * </p>
 *
 * @author hxy
 * @since 2019-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Theme implements Serializable {

private static final long serialVersionUID=1L;

    private Integer themeId;

    private String themeName;


}
