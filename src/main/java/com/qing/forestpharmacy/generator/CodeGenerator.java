package com.qing.forestpharmacy.generator;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.IFill;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

import java.util.List;

public class CodeGenerator {

    // 数据库连接信息
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/forest_pharmacy?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static final String JDBC_PASSWORD = "946626";
    private static final String JDBC_USERNAME = "root";

    //获取项目路径
    private static final String USER_DIR = System.getProperty("user.dir");
    /**
     * 包配置 -父级目录
     */
    private static final String PACKAGE_PARENT = "com.qing";

    /**
     * 包配置--模块目录，
     */
    private static final String PACKAGE_MODULE_NAME = "forestpharmacy";


    /**
     * 包配置 - 实体类名称
     */
    private static final String PACKAGE_ENTITY = "pojo";

    /**
     * 数据源配置
     */
    private static DataSourceConfig dataSourceConfig() {
        DataSourceConfig config = new DataSourceConfig.Builder(JDBC_URL,JDBC_USERNAME,JDBC_PASSWORD)
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler())
                .build();;
        return config;
    }
    /***
     * 代码生成策略配置
     * */
    private static List<IFill> create = List.of(new Property("createTime", FieldFill.INSERT),new Property("creator", FieldFill.INSERT),new Property("updateTime", FieldFill.INSERT));
    private static List<IFill> update = List.of(new Property("updateTime", FieldFill.UPDATE),new Property("updateUser", FieldFill.UPDATE));
    private static StrategyConfig strategyConfig() {

        StrategyConfig config = new StrategyConfig.Builder()
                .enableCapitalMode()
                .enableSkipView()
                .disableSqlFilter()
                .addInclude()
                .entityBuilder()//实体类
                .addTableFills(create)
                .addTableFills(update)
                .enableLombok()
                .enableFileOverride()
                .controllerBuilder()//控制器
                .enableRestStyle()
                .enableFileOverride()
                .serviceBuilder()//服务层
                .enableFileOverride()
                .mapperBuilder()//mapper层
                .enableFileOverride()
                .build();
        return config;
    }
    /**
     * 全局配置*/
    private static GlobalConfig globalConfig() {
        GlobalConfig config = new GlobalConfig.Builder().outputDir(USER_DIR+ "/src/main/java").build();
        return config;
    }

    /**
     * 包配置
     */
    private static PackageConfig packageConfig() {
        PackageConfig config = new PackageConfig.Builder()
                .parent(PACKAGE_PARENT)
                .moduleName(PACKAGE_MODULE_NAME)
                .entity(PACKAGE_ENTITY)
                .build();
        return config;
    }

    public static void main(String[] args) {
        AutoGenerator generator = new AutoGenerator(dataSourceConfig());
        generator.strategy(strategyConfig());
        generator.global(globalConfig());
        generator.packageInfo(packageConfig());
        generator.execute();
    }
}
