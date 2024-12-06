package com.qing.forestpharmacy.service.impl;

import com.qing.forestpharmacy.pojo.Employee;
import com.qing.forestpharmacy.mapper.EmployeeMapper;
import com.qing.forestpharmacy.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2024-12-06
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
