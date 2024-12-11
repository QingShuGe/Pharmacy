package com.qing.forestpharmacy.service.impl;

import com.qing.forestpharmacy.pojo.User;
import com.qing.forestpharmacy.mapper.UserMapper;
import com.qing.forestpharmacy.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2024-12-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
