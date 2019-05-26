package me.example.springBootDemo.config;


import me.example.springBootDemo.mapper.UserMapper;
import me.example.springBootDemo.tool.DBShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;


@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }

    /*@Bean(value = "sessionIdCookie")
    public Cookie sessionIdCookie(){
        SimpleCookie cookie = new SimpleCookie();
        cookie.setHttpOnly(true);
        cookie.setName("sid");
        cookie.setMaxAge(3600*24);
        return cookie;
    }*/

    @Bean("rememberMeCookie")
    public Cookie rememberMeCookie(){
        Cookie cookie = new SimpleCookie();
        cookie.setName("rememberMe");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600*24*7);
        return cookie;
    }

    @Bean
    public RememberMeManager rememberMeManager(Cookie rememberMeCookie){
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCookie(rememberMeCookie);
        return rememberMeManager;
    }

    @Bean
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //sessionManager.setSessionIdCookieEnabled(true);
        //禁用会话调度器（负责清除服务器过期session）
        sessionManager.setSessionValidationSchedulerEnabled(false);
        //sessionManager.setSessionIdCookie(sessionIdCookie);
        return sessionManager;
    }

    @Bean
    public SubjectDAO subjectDAO(){
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator());
        return subjectDAO;
    }

    @Bean
    public SessionStorageEvaluator sessionStorageEvaluator(){
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        //禁止创建session
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    @Bean
    public HashedCredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        credentialsMatcher.setHashIterations(2);
        credentialsMatcher.setHashAlgorithmName("md5");
        return credentialsMatcher;
    }

    @Bean
    public SecurityManager securityManager(DBShiroRealm dbShiroRealm,
                                            SessionManager sessionManager,
                                            RememberMeManager rememberMeManager,
                                            SubjectDAO subjectDAO){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
        securityManager.setRealm(dbShiroRealm);
        securityManager.setSubjectDAO(subjectDAO);
        securityManager.setRememberMeManager(rememberMeManager);
        //SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    @Bean
    public DBShiroRealm dbShiroRealm(UserMapper userMapper, RedisTemplate redisTemplate, CredentialsMatcher credentialsMatcher){
        DBShiroRealm dbShiroRealm = new DBShiroRealm(userMapper,redisTemplate);
        dbShiroRealm.setCredentialsMatcher(credentialsMatcher);
        return dbShiroRealm;
    }

    //下面两个bean配置允许@RequiresRole和@RequoresPremiss注解
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


}
