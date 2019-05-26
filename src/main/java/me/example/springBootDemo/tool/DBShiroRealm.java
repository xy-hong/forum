package me.example.springBootDemo.tool;

import me.example.springBootDemo.custom.RedisKeyPrefix;
import me.example.springBootDemo.custom.Role;
import me.example.springBootDemo.entity.User;
import me.example.springBootDemo.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.data.redis.core.RedisTemplate;


import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;


public class DBShiroRealm extends AuthorizingRealm{

    private UserMapper userMapper;

    private RedisTemplate redisTemplate;

    public DBShiroRealm(UserMapper userMapper,RedisTemplate redisTemplate){
        this.userMapper =userMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        User user = userMapper.selectById(username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(this.RoleTypeResolve(user.getRole()));
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        User user = userMapper.selectById(username);

        System.out.println(user);

        if (user==null) {
            throw new UnknownAccountException();
        }
        //短时间内登录次数限制五次
        if(this.isLoginLimit(username)){
            throw new ExcessiveAttemptsException();
        }

        //如果用户是黑名单用户，3代表黑名单
        if(user.getRole() == Role.BLACKLIST){
            throw new LockedAccountException();
        }

        //验证失败会抛出异常
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), this.getName());
        return info;
    }

    /**
     * 使用redis限制短时间内登录尝试次数
     * true表示限制
     */
    private boolean isLoginLimit(String username){
        String key = RedisKeyPrefix.LOGIN_LIMIT+":"+username;
        Integer times = (Integer) redisTemplate.opsForValue().get(key);
        if (times==null){
            redisTemplate.opsForValue().set(key,1,10, TimeUnit.MINUTES);
        } else{
            redisTemplate.opsForValue().set(key,++times,10,TimeUnit.MINUTES);

            if (times > 5){
                return true;
            }
        }
        return false;
    }

    // 根据传入的byte值获取对应的Role字符串
    private String RoleTypeResolve(byte role){
        Class<Role> roleClass = Role.class;
        Field[] fields = roleClass.getDeclaredFields();

        for (Field field : fields){
            try {
                if (role == field.getByte(null)){
                    return field.getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
