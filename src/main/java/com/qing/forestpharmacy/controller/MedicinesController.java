package com.qing.forestpharmacy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qing.forestpharmacy.common.result.Result;
import com.qing.forestpharmacy.pojo.Category;
import com.qing.forestpharmacy.pojo.Medicines;
import com.qing.forestpharmacy.service.IMedicinesService;
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
@RestController
@RequestMapping("/medicines")
public class MedicinesController {

    @Autowired
    private IMedicinesService medicinesService;

    @PostMapping("/add")
    public Result<String> add(@RequestBody Medicines medicines) {
        if(medicinesService.save(medicines)){
            return Result.success("药品添加成功");
        }
        return Result.failed("药品添加失败");
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody Medicines medicines) {
        if (medicinesService.updateById(medicines)) {
            return Result.success("药品更新成功");
        }
        return Result.failed("药品更新失败");
    }

    @DeleteMapping("/delete")
    public Result<String> delete(@RequestParam("id") Long id) {
        if(!medicinesService.removeCategoryById(id)){
            return Result.failed("删除失败");
        }
        return Result.success("删除成功");
    }

    @GetMapping("/get")
    public Result<Medicines> get(@RequestParam("id") String id){
        Medicines medicines = medicinesService.getById(id);
        return Result.success(medicines);
    }

    @GetMapping("/list")
    public Result<Result.Data<Medicines>> list(String name,Integer status,Integer category,@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "10") int pageSize){
        //分页查询
        IPage<Medicines> medicinesPage = new Page<>(page,pageSize);

        LambdaQueryWrapper<Medicines> wrapper = new LambdaQueryWrapper<>();
        //模糊查询药品名称或备注
        wrapper.like(name!= null && !name.isEmpty(),Medicines::getMedicineName,name).or().like(name!= null && !name.isEmpty(),Medicines::getRemark,name);
        //根据药品状态查询
        wrapper.eq(status != null && status!= 0,Medicines::getStatus,status);

        //根据药品分类查询
        wrapper.eq(category!= null && category!= 0,Medicines::getCategoryId,category);
        //排序
        wrapper.orderByDesc(Medicines::getCreateTime);
        //分页查询
        medicinesService.page(medicinesPage,wrapper);
        return Result.PageRresult(medicinesPage);
    }
}
