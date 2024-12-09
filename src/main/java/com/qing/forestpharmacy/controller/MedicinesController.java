package com.qing.forestpharmacy.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2024-12-09
 */
@RestController
@RequestMapping("/medicines")
public class MedicinesController {
    @RequiresPermissions("medicines:*")
    @GetMapping("/list")
    public String list() {
        return "list";
    }
}
