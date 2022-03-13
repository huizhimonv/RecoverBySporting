package com.example.recoverbysporting.controller.system;

import com.example.recoverbysporting.entity.Doctor;
import com.example.recoverbysporting.entity.Log;
import com.example.recoverbysporting.service.LogService;
import com.example.recoverbysporting.service.UserService;
import com.example.recoverbysporting.service.UserServiceImpl;
import com.example.recoverbysporting.utils.ResultBody;
import com.example.recoverbysporting.utils.page.PageRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 该模块针对超级管理员使用
 */
@RestController
@RequestMapping("/system")
public class SystemApi {
    @Autowired
    UserService userService;
    @Autowired
    LogService logService;

    /**
     * 查看所有管理员的信息：name、account、password、organizationName、date(创建时间)
     */
    @RequiresRoles("super")
    @RequestMapping(value = "/allAdmin",method = RequestMethod.POST)
    public Object findAll(@RequestBody PageRequest pageRequest){
        logService.insert(new Log(getIdAndDate().getDid(), "查看所有管理员", getIdAndDate().getDate(), "成功"));
        return new ResultBody<>(true,200,userService.findPage(pageRequest));
    }

    /**
     * 重置密码  统一设置为123456加密后的结果
     * @param id
     * @return
     */
    @RequiresRoles("super")
    @RequestMapping(value = "reset",method = RequestMethod.GET)
    public Object reset(@RequestParam int id){
        if(id <= 0){
            return new ResultBody<>(false,501,"error id");
        }
        userService.reset(id);
        String name = userService.getById(id).getName();
        logService.insert(new Log(getIdAndDate().getDid(), "对"+"["+name+"]"+"进行密码重置操作", getIdAndDate().getDate(), "成功"));
        return new ResultBody<>(true,200,null);
    }

    /**
     * 新增管理员 name、account、oid     date、password由后端补充
     * @param doctor
     * @return
     */
    @RequiresRoles("super")
    @RequestMapping(value = "/insertAdmin",method = RequestMethod.POST)
    public Object insert(@RequestBody Doctor doctor){
        if(userService.getUserByAccount(doctor.getAccount()) != null){
            //执行更新操作
            userService.updateRole(doctor.getAccount());
        }else {
            //执行插入操作
            String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            doctor.setDate(date);
            doctor.setPassword("e10adc3949ba59abbe56e057f20f883e");
            String role = "{\"roles\":[\"admin\"]}";
            doctor.setRole(role);
            doctor.setDisable(false);
            userService.insert(doctor);
        }
        logService.insert(new Log(getIdAndDate().getDid(), "插入操作", getIdAndDate().getDate(), "成功"));
        return new ResultBody<>(true,200,null);
    }
    /**
     * doctor : id,account,name,oid
     * 修改管理员信息
     */
    @RequiresRoles("super")
    @RequestMapping("/update")
    public Object update(@RequestBody Doctor doctor){
        if(userService.getUserByAccount(doctor.getAccount()) != null){
            doctor.setRole(userService.getUserByAccount(doctor.getAccount()).getRole());
            //更新操作
            userService.update(doctor);
            if(doctor.getId() == null){
                return new ResultBody<>(false,400,"missing id");
            }
        }
        userService.update(doctor);
        logService.insert(new Log(getIdAndDate().getDid(), "更新操作", getIdAndDate().getDate(), "成功"));
        return new ResultBody<>(true,200,null);
    }
    /**
     * 删除管理员
     */
    @RequiresRoles("super")
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object delete(@RequestParam int id){
        if(id <= 0){
            return new ResultBody<>(false,501,"error id");
        }
        String name = userService.getById(id).getName();
        if(userService.getRoleByUid(id).size() >= 2){
            //更新操作
            userService.deleteRole(id);
        }else{
            //删除操作
            userService.delete(id);
        }
        logService.insert(new Log(getIdAndDate().getDid(), "撤销"+"["+name+"]"+"的管理员身份并删除", getIdAndDate().getDate(), "成功"));
        return new ResultBody<>(true,200,null);
    }
    /**
     * 禁用管理员功能
     */
    @RequiresRoles("super")
    @RequestMapping(value = "/setDisable",method = RequestMethod.GET)
    public Object disable(@RequestParam int id){
        if(id <= 0){
            return new ResultBody<>(false,501,"error id");
        }
        //标记该账号已被禁用
        userService.disable(id);
        String name = userService.getById(id).getName();
        logService.insert(new Log(getIdAndDate().getDid(), "禁用"+"["+name+"]"+"的管理员身份", getIdAndDate().getDate(), "成功"));
        return new ResultBody<>(true,200,null);
    }

    /**
     * 启用管理员功能
     */
    @RequiresRoles("super")
    @RequestMapping(value = "/cancelDisable",method = RequestMethod.GET)
    public Object cancelDisable(@RequestParam int id){
        if(id <= 0){
            return new ResultBody<>(false,501,"error id");
        }
        //标记该账号已被启用
        userService.cancelDisable(id);
        String name = userService.getById(id).getName();
        logService.insert(new Log(getIdAndDate().getDid(), "启用"+"["+name+"]"+"的管理员身份", getIdAndDate().getDate(), "成功"));
        return new ResultBody<>(true,200,null);
    }


    private  Log getIdAndDate(){
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());
        return new Log(doctor.getId(),date);
    }
}
