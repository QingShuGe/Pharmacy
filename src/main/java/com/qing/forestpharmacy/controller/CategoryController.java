package com.qing.forestpharmacy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qing.forestpharmacy.common.result.Result;
import com.qing.forestpharmacy.pojo.AdminUser;
import com.qing.forestpharmacy.pojo.Category;
import com.qing.forestpharmacy.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;
    @PostMapping("/add")
    public Result<String> add(@RequestBody Category category) {
        if(categoryService.save(category)){
            return Result.success("分类添加成功");
        }
        return Result.failed("分类添加失败");
    }

    @DeleteMapping("/delete")
    public Result<String> delete(@RequestParam("id") Long id) {
        if(!categoryService.removeCategoryById(id)){
            return Result.failed("删除失败");
        }
        return Result.success("删除成功");
    }
    @PostMapping("/update")
    public Result<String> update(@RequestBody Category category) {
        if (categoryService.updateById(category)) {
            return Result.success("分类更新成功");
        }
        return Result.failed("分类更新失败");
    }
    @GetMapping("/get")
    public Result<Category> get(@RequestParam("id") String id){
        Category categorie = categoryService.getById(id);
        return Result.success(categorie);
    }

    @GetMapping("/list")
    public Result<Result.Data<Category>> list(String name,Long father,@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "10") int pageSize){
        //分页查询
        IPage<Category> categoryPage = new Page<>(page,pageSize);

        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        //模糊查询分类名称或备注
        wrapper.like(name!= null && !name.isEmpty(),Category::getTypeName,name).or().like(name!= null && !name.isEmpty(),Category::getRemark,name);
        //模糊查询手机号
        wrapper.eq(father!= null && father!= 0,Category::getFatherId,father);
        //排序
        wrapper.orderByAsc(Category::getSort).orderByDesc(Category::getCreateTime);
        //分页查询
        categoryService.page(categoryPage,wrapper);
        return Result.PageRresult(categoryPage);
    }
}
