package com.example.recoverbysporting.shiro;

import com.example.recoverbysporting.entity.Doctor;
import com.example.recoverbysporting.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;


public class CustomRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;

    /**
     * 在controller中添加注解的时候会调用该方法
     * @param principalCollection
     * @return 返回授权信息，触发shiro的授权机制
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取账号
        String account = (String) principalCollection.getPrimaryPrincipal();
        //通过用户名查找用户
        Doctor doctor = userService.getUserByAccount(account);
        //添加角色和权限，SimpleAuthorizationInfo：授权信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for(String role : userService.getRoleByUid(doctor.getId())){
            simpleAuthorizationInfo.addRole(role);
        }
        for(String permission : userService.getPermissionByUid(doctor.getId())){
            simpleAuthorizationInfo.addStringPermission(permission);
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 用户在登录的时候会调用的爱方法，实现登录验证
     * @param authenticationToken
     * @return 返回授权信息
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //该StringUtils是在spring框架下的
        if (StringUtils.isEmpty(authenticationToken.getPrincipal())) {
            return null;
        }
        //通过token获得账号
        String account = authenticationToken.getPrincipal().toString();
        Doctor doctor = userService.getUserByAccount(account);
        if (doctor == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            //这里可以存放用户的相关信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(doctor.getAccount(), doctor.getPassword(), getName());
            return simpleAuthenticationInfo;
        }
    }
}
