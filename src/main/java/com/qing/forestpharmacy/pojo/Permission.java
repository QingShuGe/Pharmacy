package com.qing.forestpharmacy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author baomidou
 * @since 2024-12-10
 */
@Getter
@Setter
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限表主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 权限名
     */
    private String permissionName;

    /**
     * 与role角色表联系的外键
     */
    private Integer roleId;
}
