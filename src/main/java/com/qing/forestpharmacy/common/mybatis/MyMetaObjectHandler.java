package com.qing.forestpharmacy.common.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.qing.forestpharmacy.common.utils.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始插入填充...");
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("creator", BaseContext.getCurrentThreadId());
        metaObject.setValue("updateUser",BaseContext.getCurrentThreadId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始更新填充...");

        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser",BaseContext.getCurrentThreadId());
    }
}