package com.qing.forestpharmacy.service;

import com.qing.forestpharmacy.pojo.AdminUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qing.forestpharmacy.shiro.entity.SecurityDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2024-12-10
 */
public interface IAdminUserService extends IService<AdminUser> {
    SecurityDTO getRolename(String username);
}
