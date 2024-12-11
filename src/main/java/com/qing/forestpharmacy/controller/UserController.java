package com.qing.forestpharmacy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qing.forestpharmacy.common.result.Result;
import com.qing.forestpharmacy.common.utils.JwtUtils;
import com.qing.forestpharmacy.pojo.User;
import com.qing.forestpharmacy.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2024-12-10
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public Result<String> login(@RequestParam(name = "username") String userName,
                                @RequestParam(name = "password") String password) {
        // 验证用户名和密码
        User phone = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getPhone, userName).eq(User::getPassword,password).last("LIMIT 1"));
        if (phone == null) {
            return Result.reCode("A0210");
        }
        // 生成token
        String token = JwtUtils.generateToken(phone.getId(), phone.getUsername());

        return Result.success("登录成功", token);
    }
    @GetMapping("/logout")
    public Result<String> logout() {
        return Result.reCode("A02004");
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody User user) {


//        生成账号
//        user.setUsername("21312");
        user.setStatus(1);
        user.setAvatar("1234.jpg");
        user.setRecommend(0);

        // 验证手机号是否已注册
        long count = userService.count(new LambdaQueryWrapper<User>().eq(User::getPhone, user.getPhone()));

        if (count > 0) {
            return Result.reCode("A0201");
        }
        // 注册用户
        boolean result = userService.save(user);
        if (result) {
            return Result.success("注册成功");
        }
        return Result.failed("注册失败");
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody User user) {
        boolean update = userService.updateById(user);
        if(update){
            return Result.success("更新成功");
        }
        return Result.failed("更新失败，请检查字段");
    }

    @GetMapping("/info")
    public Result<User> userinfo() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, 1);
        User user = userService.getOne(wrapper);
        if(user != null){
            return Result.success(user);
        }
        return Result.failed("获取用户信息失败");
    }
}
