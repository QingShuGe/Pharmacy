package com.qing.forestpharmacy.controller;

import com.qing.forestpharmacy.pojo.Employee;
import com.qing.forestpharmacy.service.IEmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2024-12-06
 */
@Tag(name = "员工管理")
@RestController
@RequestMapping(value = "/employee" , produces = "application/json;charset=UTF-8")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;

    @Operation(summary = "测试",description = "测试Accept")
    @GetMapping(value = "/get")
    public Employee get(Employee employee){
        return employeeService.list().get(0);
    }
}
