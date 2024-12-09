package com.qing.forestpharmacy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qing.forestpharmacy.pojo.Permission;
import com.qing.forestpharmacy.pojo.Role;
import com.qing.forestpharmacy.pojo.User;
import com.qing.forestpharmacy.mapper.UserMapper;
import com.qing.forestpharmacy.service.IPermissionService;
import com.qing.forestpharmacy.service.IRoleService;
import com.qing.forestpharmacy.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qing.forestpharmacy.shiro.entity.SecurityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2024-12-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;
    public SecurityDTO getRolename(String username) {
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if(user != null) {
            SecurityDTO securityDTO = new SecurityDTO();
            securityDTO.setUsername(user.getUsername());


            //获取角色列表
            String[] roles = user.getRoles().split(",");
            //根据权限id获取角色列表
            List<Role> roleList = roleService.listByIds(Arrays.asList(roles));

            securityDTO.setRoleList(roleList);

            List<Permission> permissionList = new ArrayList<>();
            //获取权限列表
            for(String role:roles){
                permissionList.add(permissionService.getOne(new LambdaQueryWrapper<Permission>().eq(Permission::getRoleId, role)));
            }
            securityDTO.setPermissionList(permissionList);
            return securityDTO;
        }

        return null;
    }
}
