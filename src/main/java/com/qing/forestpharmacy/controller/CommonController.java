package com.qing.forestpharmacy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qing.forestpharmacy.common.result.Result;
import com.qing.forestpharmacy.pojo.Orders;
import com.qing.forestpharmacy.pojo.User;
import com.qing.forestpharmacy.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
@RequestMapping("/common")
public class CommonController {
    @Value("${file_path.path}")
    private String basePath;

    @Autowired
    private IUserService userService;

    @PostMapping("upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        log.info("upload file: {}", file.getOriginalFilename());
        //获取文件名
        String fileObjName = file.getOriginalFilename();
        //获取文件后缀
        String suffix = fileObjName.substring(fileObjName.lastIndexOf("."));
        //生成新的文件名
        String fileName = UUID.randomUUID() + suffix;

        //目录
        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //保存文件
        file.transferTo(new File(basePath, fileName));
        return Result.success(fileName);
    }

    @PutMapping("/setAvatar")
    public Result<String> setAvatar(MultipartFile file) throws IOException {
        log.info("upload file: {}", file.getOriginalFilename());
        //获取文件名
        String fileObjName = file.getOriginalFilename();
        //获取文件后缀
        String suffix = fileObjName.substring(fileObjName.lastIndexOf("."));
        //生成新的文件名
        String fileName = UUID.randomUUID() + suffix;

        //目录
        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //保存文件
        file.transferTo(new File(basePath, fileName));

        String avatar =basePath +"\\"+ fileName;
        //更新用户头像
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", 1).set("avatar", avatar);
        boolean result = userService.update(updateWrapper); // 调用 update 方法
        if(result){
            return Result.success("更新头像成功", avatar);
        }
        return Result.failed("更新头像失败");
    }
}
