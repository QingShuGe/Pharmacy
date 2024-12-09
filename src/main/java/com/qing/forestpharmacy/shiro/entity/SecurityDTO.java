package com.qing.forestpharmacy.shiro.entity;

import com.qing.forestpharmacy.pojo.Permission;
import com.qing.forestpharmacy.pojo.Role;
import lombok.Data;

import java.util.List;

@Data
public class SecurityDTO {
    private String username;
    /**
     * 用户拥有的角色
     */
    private List<Role> roleList;

    /**
     * 用户拥有的权限
     */
    private List<Permission> permissionList;
}
