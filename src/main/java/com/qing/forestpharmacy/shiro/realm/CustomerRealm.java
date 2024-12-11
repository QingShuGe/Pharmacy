package com.qing.forestpharmacy.shiro.realm;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.qing.forestpharmacy.common.utils.JwtUtils;
import com.qing.forestpharmacy.pojo.Permission;
import com.qing.forestpharmacy.pojo.Role;
import com.qing.forestpharmacy.service.IAdminUserService;
import com.qing.forestpharmacy.service.IUserService;
import com.qing.forestpharmacy.shiro.entity.SecurityDTO;
import com.qing.forestpharmacy.shiro.token.JwtToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Component
public class CustomerRealm extends AuthorizingRealm {

    @Autowired
    private IAdminUserService adminUserService;

    // 自定义的token
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 从token中获取username
        String username = JwtUtils.getClaim(principalCollection.toString(), "username");

        // 从数据库中获取用户角色和权限信息
        SecurityDTO userRole = adminUserService.getRolename(username);

        //当前用户的角色信息，权限信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 赋值给权限对象
        List<Role> roles = userRole.getRoleList();
        for (Role role : roles) {
            simpleAuthorizationInfo.addRole(role.getRoleName());
        }

        // 赋值权限信息
        List<Permission> Permissions = userRole.getPermissionList();
        List<String> permissionList = new ArrayList<>();
        for(Permission p : Permissions){
            if (StringUtils.isNotBlank(p.getPermissionName())) {
                permissionList.add(p.getPermissionName());
            }
        }

        simpleAuthorizationInfo.addStringPermissions(new HashSet<>(permissionList));

        return simpleAuthorizationInfo;
    }


    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取token
        String token = (String) authenticationToken.getCredentials();

        JwtUtils.verify(token);

//        if (userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username).last("LIMIT 1")) == null) {
//            throw new AuthenticationException("用户不存在");
//        }

        return new SimpleAuthenticationInfo(token, token, this.getName());
    }
}

