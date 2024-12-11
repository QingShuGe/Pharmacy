package com.qing.forestpharmacy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qing.forestpharmacy.pojo.Medicines;
import com.qing.forestpharmacy.mapper.MedicinesMapper;
import com.qing.forestpharmacy.pojo.RevenueAnalysis;
import com.qing.forestpharmacy.service.IMedicinesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qing.forestpharmacy.service.IRevenueAnalysisService;
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
public class MedicinesServiceImpl extends ServiceImpl<MedicinesMapper, Medicines> implements IMedicinesService {
    @Autowired
    private IRevenueAnalysisService revenueAnalysisService;

    @Override
    public boolean removeCategoryById(Long id) {
        LambdaQueryWrapper<RevenueAnalysis> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(RevenueAnalysis::getMedicineId, id);
        long count = revenueAnalysisService.count(dishLambdaQueryWrapper);
        if (count > 0) {
            UpdateWrapper<Medicines> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id);
            updateWrapper.set("status", 1);
            return this.update(updateWrapper);
        }
        return this.removeById(id);
    }
}
