package com.example.sideproject.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();

        //filter
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/login", "anon");
        filterMap.put("/", "roles[user]");
        filterMap.put("/edit", "roles[user]");
        filterMap.put("/transfer", "roles[user]");
        filterMap.put("/home", "roles[user]");
        filterMap.put("/add", "roles[user]");
        filterMap.put("/tasks", "roles[user]");
        filterMap.put("/task", "roles[user]");
        filterMap.put("/search", "roles[user]");
        filterMap.put("/done", "roles[user]");
        bean.setLoginUrl("/login");
        bean.setFilterChainDefinitionMap(filterMap);
        bean.setSecurityManager(securityManager);
        return bean;
    }


    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

}
