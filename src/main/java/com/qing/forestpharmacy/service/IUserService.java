package com.qing.forestpharmacy.service;

import com.qing.forestpharmacy.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qing.forestpharmacy.shiro.entity.SecurityDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2024-12-09
 */
public interface IUserService extends IService<User> {
    SecurityDTO getRolename(String username);
}
