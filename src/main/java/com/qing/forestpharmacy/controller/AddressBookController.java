package com.qing.forestpharmacy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qing.forestpharmacy.common.result.Result;
import com.qing.forestpharmacy.pojo.AddressBook;
import com.qing.forestpharmacy.pojo.Category;
import com.qing.forestpharmacy.service.IAddressBookService;
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
@RequestMapping("/addressBook")
public class AddressBookController {
    @Autowired
    private IAddressBookService addressBookService;

    @PostMapping("/add")
    public Result<String> add(@RequestBody AddressBook addressBook) {

        //设置用户id
        addressBook.setUserId(1L);
        boolean add = addressBookService.save(addressBook);
        if(add){
            return Result.success("地址添加成功");
        }
        return Result.failed("地址添加失败");
    }

    @DeleteMapping("delete")
    public Result<String> delete(@RequestParam("id") Long id) {
        boolean delete = addressBookService.removeById(id);
        if(delete){
            return Result.success("地址删除成功");
        }
        return Result.failed("删除失败，地址不存在");
    }

    @PostMapping("update")
    public Result<String> update(@RequestBody AddressBook addressBook) {
        boolean update = addressBookService.updateById(addressBook);
        if(update){
            return Result.success("更新成功");
        }
        return Result.failed("更新失败，请检查字段");
    }

    @GetMapping("/get")
    public Result<AddressBook> get(@RequestParam("id") String id){
        AddressBook addressBook = addressBookService.getById(id);
        if(addressBook == null){
            return Result.failed("地址不存在");
        }
        return Result.success(addressBook);
    }

    @GetMapping("list")
    public Result<List<AddressBook>> list() {

        //按照默认地址和更新时间排序
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();

        //根据登录用户id查询收货地址
        wrapper.eq(AddressBook::getUserId, 1);
        wrapper.orderByAsc(AddressBook::getIsDefault).orderByAsc(AddressBook::getUpdateTime);
        List<AddressBook> list = addressBookService.list();
        if(list.size()>0){
            return Result.success(list);
        }

        return Result.failed("该用户还没有收货地址");
    }
    @GetMapping("default")
    public Result<AddressBook> getDefault() {
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressBook::getUserId, 1);
        wrapper.eq(AddressBook::getIsDefault, true);
        AddressBook addressBook = addressBookService.getOne(wrapper);
        if(addressBook != null){
            return Result.success(addressBook);
        }
        return Result.failed("没有找到默认地址");
    }
    /*设置默认地址*/
    @PostMapping("setDefalut")
    public Result<String> setDefalut(@RequestBody AddressBook addressBook) {

        //先将其他地址的默认值设置为0
        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getUserId, 1);
        wrapper.set(AddressBook::getIsDefault, false);
        addressBookService.update(wrapper);

        //再将当前地址的默认值设置为1
        addressBook.setIsDefault(true);
        boolean update = addressBookService.updateById(addressBook);
        if(update){
            return Result.success("设置默认地址成功");
        }
        return Result.failed("设置默认地址失败");
    }
}
