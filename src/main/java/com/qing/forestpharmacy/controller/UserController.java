package com.qing.forestpharmacy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qing.forestpharmacy.common.result.Result;
import com.qing.forestpharmacy.common.utils.JwtUtils;
import com.qing.forestpharmacy.pojo.User;
import com.qing.forestpharmacy.service.IUserService;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2024-12-09
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public Result<String> login(@RequestParam(name = "username", required = true) String userName,
                                @RequestParam(name = "password", required = true) String password, ServletResponse response) {
        if (userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, userName).eq(User::getPassword,password).last("LIMIT 1")) == null) {
            return Result.reCode("A0210");
        }
        String token = JwtUtils.generateToken(userName);
        ((HttpServletResponse) response).setHeader("token", token);

        return Result.success("登录成功", token);
    }
    @GetMapping("/logout")
    public Result<String> logout() {
        return Result.success("A02004");
    }
}
