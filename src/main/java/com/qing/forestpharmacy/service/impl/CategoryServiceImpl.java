package com.qing.forestpharmacy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qing.forestpharmacy.pojo.Category;
import com.qing.forestpharmacy.mapper.CategoryMapper;
import com.qing.forestpharmacy.pojo.Medicines;
import com.qing.forestpharmacy.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qing.forestpharmacy.service.IMedicinesService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private IMedicinesService medicinesService;

    @Override
    public boolean removeCategoryById(Long id) {
        LambdaQueryWrapper<Medicines> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Medicines::getCategoryId, id);
        Long count = medicinesService.count(dishLambdaQueryWrapper);
        if (count > 0) {
            throw new RuntimeException("该分类下存在药品，不能删除");
        }
        return this.removeById(id);
    }
}
