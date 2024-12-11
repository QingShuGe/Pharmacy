package com.qing.forestpharmacy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qing.forestpharmacy.common.result.Result;
import com.qing.forestpharmacy.pojo.Category;
import com.qing.forestpharmacy.pojo.Orders;
import com.qing.forestpharmacy.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
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
@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private IOrdersService ordersService;

    @PostMapping("/add")
    public Result<String> add(@RequestBody Orders order) {

        order.setExpressNumber("");
        order.setCheckoutTime(null);
        order.setPayMethod(null);

        if(ordersService.save(order)){
            return Result.success("订单添加成功");
        }
        return Result.failed("订单添加失败");
    }

    @DeleteMapping("/deleteList")
    public Result<String> delete(@RequestParam("id") List<Integer> ids) {
        if(!ordersService.removeByIds(ids)){
            return Result.failed("批量删除失败");
        }
        return Result.success("批量删除成功");
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody Orders order) {

        if (ordersService.updateById(order)) {
            return Result.success("订单更新成功");
        }
        return Result.failed("订单更新失败");
    }

    @GetMapping("/get")
    public Result<Orders> get(@RequestParam("id") Integer id){
        Orders order = ordersService.getOne(new LambdaQueryWrapper<Orders>().eq(Orders::getUserId,id));
        return Result.success(order);
    }

    //        根据用户id查询
    @GetMapping("/user")
    public Result<Result.Data<Orders>> userOrderList(String name,Long order,Long express,Integer status,@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "10") int pageSize){
        //分页查询
        IPage<Orders> ordersPage = new Page<>(page,pageSize);

        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();

            //根据用户id查询
        wrapper.eq(Orders::getUserId,1);

        //根据是否删除查询
//        wrapper.eq(Orders::getisDelete,1);
        //根据订单状态查询
        wrapper.eq(status!=null && status!=0,Orders::getStatus,status);
        //模糊查询药品名
        wrapper.like(name!= null && !name.isEmpty(),Orders::getMedicineName,name);
        //模糊查询订单号，快递单号
        wrapper.like(order!= null && order !=0,Orders::getOrderNumber,order).or().like(express!= null && express!=0,Orders::getExpressNumber,express);
        //排序
        wrapper.orderByDesc(Orders::getOrderTime);
        //分页查询
        ordersService.page(ordersPage,wrapper);
        return Result.PageRresult(ordersPage);
    }
    @GetMapping("/list")
    public Result<Result.Data<Orders>> list(String name,Integer order,Integer express,Integer status,@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "10") int pageSize){
        //分页查询
        IPage<Orders> ordersPage = new Page<>(page,pageSize);

        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();

        //模糊查询药品名
        wrapper.like(name!= null && !name.isEmpty(),Orders::getMedicineName,name);
        //根据订单状态查询
        wrapper.eq(status!= null && status !=0,Orders::getStatus,status);
        //模糊查询手机号
        wrapper.like(order!= null && order !=0,Orders::getOrderNumber,order).or().like(express!= null && express!=0,Orders::getExpressNumber,express);
        //排序
        wrapper.orderByDesc(Orders::getOrderTime);
        //分页查询
        ordersService.page(ordersPage,wrapper);
        return Result.PageRresult(ordersPage);
    }
}
