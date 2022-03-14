package com.example.recoverbysporting.controller.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.entity.Doctor;
import com.example.recoverbysporting.service.OrganizationService;
import com.example.recoverbysporting.service.UserService;
import com.example.recoverbysporting.utils.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

@RestController
@Slf4j
public class LoginApi {
    @Autowired
    UserService userService;
    @Autowired
    OrganizationService organizationService;
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object login(@RequestParam String account,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        SecurityUtils.getSubject().getSession().setTimeout(-1000L);
        Doctor doctor = new Doctor();
        doctor.setAccount(account);
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        doctor.setPassword(password);
        //这里判断用户的账号是否被禁用
        if(userService.getUserByAccount(account) == null){
            return new ResultBody<>(false,500,"用户名不存在");
        }else {
            if(userService.getUserByAccount(account).isDisable()){
                return new ResultBody<>(false,503,"该账号已被禁用");
            }
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                doctor.getAccount(),
                doctor.getPassword()
        );
        JSONObject res = new JSONObject();
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
            String JSESSIONID = (String) subject.getSession().getId();
            res.put("JSESSIONID",JSESSIONID);
            res.put("role",userService.getRoleByUid(userService.getUserByAccount(account).getId()));
//            subject.checkRole("admin");
//            subject.checkRole("staff");
        } catch (UnknownAccountException e) {
            log.error("用户名不存在！", e);
            return new ResultBody<>(false,500,"用户名不存在");
        } catch (AuthenticationException e) {
            log.error("账号或密码错误！", e);
            return new ResultBody<>(false,501,"账号或密码错误！");
        } catch (AuthorizationException e) {
            log.error("没有权限！", e);
            return new ResultBody<>(false,502,"没有权限！");
        }
        return new ResultBody<>(true,200,res);
    }

    @GetMapping("/index")
    public Object index() {
        //只能得到用户的账号 而非用户的所有信息，但是可以通过用户的账号去获取对应的信息
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        return new ResultBody<>(true,200,"login success");
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public Object logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new ResultBody<>(true,200,"logout success");
    }
    /**
     * 查看用户的信息
     */
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public Object getUserInformation(){
        JSONObject res = new JSONObject();
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        res.put("account",doctor.getAccount());
        res.put("name",doctor.getName());
        res.put("date",doctor.getDate());
        res.put("organizationName",organizationService.findByOid(doctor.getOid()).getName());
        res.put("role",userService.getRoleByUid(doctor.getId()));
        return new ResultBody<>(true,200,res);
    }
}
