package com.qing.forestpharmacy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qing.forestpharmacy.common.result.Result;
import com.qing.forestpharmacy.common.utils.JwtUtils;
import com.qing.forestpharmacy.pojo.AdminUser;
import com.qing.forestpharmacy.service.IAdminUserService;
import jakarta.servlet.ServletResponse;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
@RequestMapping("/adminUser")
public class AdminUserController {
    @Autowired
    private IAdminUserService adminUserService;

    @PostMapping("/login")
    public Result<String> login(@RequestParam(name = "username") String userName,
                                @RequestParam(name = "password") String password, ServletResponse response) {
        // 验证用户名和密码
        AdminUser user =adminUserService.getOne(new LambdaQueryWrapper<AdminUser>().eq(AdminUser::getUsername, userName).eq(AdminUser::getPassword,password).last("LIMIT 1"));
        if (user == null) {
            return Result.reCode("A0210");
        }
        // 生成token
        String token = JwtUtils.generateToken(user.getId(), user.getUsername());

        return Result.success("登录成功", token);
    }
    @GetMapping("/logout")
    public Result<String> logout() {
        return Result.reCode("A02004");
    }


    @PostMapping("/addEmployee")
    public Result<String> addEmployee(@RequestBody AdminUser user) {
        user.setStatus(1);
        boolean result = adminUserService.save(user);
        if(!result){
            return Result.success("添加员工失败");
        }
        return Result.success("添加员工成功");
    }

    @RequiresPermissions("user:*")
    @DeleteMapping("/delete")
    public Result<String> deleteEmployee(@PathParam("userid") int userid) {
        boolean result = adminUserService.removeById(userid);
        if(!result){
            return Result.success("删除员工失败");
        }
        return Result.success("删除员工成功");
    }

    @RequiresPermissions("user:*")
    @PutMapping("/update")
    public Result<String> updateEmployee(@RequestBody AdminUser user) {
        boolean result = adminUserService.updateById(user);
        if(!result){
            return Result.success("更新员工失败");
        }
        return Result.success("更新员工成功");
    }

    @RequiresAuthentication
    @GetMapping("/get")
    public Result<AdminUser> getEmployee(@PathParam("userid") int userid) {
        AdminUser user = adminUserService.getById(userid);
        return Result.success(user);
    }

    @RequiresAuthentication
    @GetMapping("/list")
    public Result<Result.Data<AdminUser>> getEmployeeList(String name, String phone, String username,@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "10") int pageSize) {
        //分页查询
        IPage<AdminUser> employeePage = new Page<>(page,pageSize);

        LambdaQueryWrapper<AdminUser> wrapper = new LambdaQueryWrapper<>();
        //模糊查询姓名
        wrapper.like(name!= null && !name.isEmpty(),AdminUser::getName,name);
        //模糊查询账号
        wrapper.like(username!= null && !username.isEmpty(),AdminUser::getName,username);
        //模糊查询手机号
        wrapper.like(phone!= null && !phone.isEmpty(),AdminUser::getPhone,phone);
        //排序
        wrapper.orderByDesc(AdminUser::getUpdateTime);
        //分页查询
        adminUserService.page(employeePage,wrapper);
        return Result.PageRresult(employeePage);
    }
}
