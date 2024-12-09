package com.qing.forestpharmacy.shiro.config;

import com.qing.forestpharmacy.common.filter.JwtFilter;
import com.qing.forestpharmacy.shiro.realm.CustomerRealm;
import jakarta.servlet.Filter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    /**
     * 添加注解支持，如果不加的话很有可能注解失效
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){

        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(defaultWebSecurityManager);
        return advisor;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    @Bean("jwtFilter")
    public JwtFilter jwtFilterBean() {
        return new JwtFilter();
    }

    //ShiroFilter过滤所有请求
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //配置JWT过滤器
        Map<String, Filter> filtermap = new LinkedHashMap<>();
        filtermap.put("jwtFilter",jwtFilterBean());
        shiroFilterFactoryBean.setFilters(filtermap);

        //给ShiroFilter配置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //配置系统公共资源
        Map<String, String> map = new LinkedHashMap<>();
        map.put("/user/login", "anon");

        map.put("/doc.html","anon");
        map.put("/webjars/**","anon");
        map.put("/swagger-ui/**","anon");
        map.put("/swagger/**","anon");
        map.put("/swagger-resources/**","anon");
        map.put("/v3/**","anon");

        map.put("/static/**", "anon");
        map.put("/**", "jwtFilter");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }


    /**
     * CustomerRealm 配置，需实现 Realm 接口
     */
    @Bean
    CustomerRealm CustomerRealm() {
        CustomerRealm realm = new CustomerRealm();
        return realm;
    }

    //创建安全管理器
    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("CustomerRealm") CustomerRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        // 关闭session
        DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        defaultSubjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
        securityManager.setSubjectDAO(defaultSubjectDAO);
        ThreadContext.bind(securityManager);
        return securityManager;
    }
}
