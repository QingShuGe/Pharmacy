package com.qing.forestpharmacy.service;

import com.qing.forestpharmacy.pojo.Medicines;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2024-12-10
 */
public interface IMedicinesService extends IService<Medicines> {
    boolean removeCategoryById(Long id);
}
