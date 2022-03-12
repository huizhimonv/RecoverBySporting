package com.example.recoverbysporting.controller.system;

import com.example.recoverbysporting.service.UserService;
import com.example.recoverbysporting.utils.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 该模块针对超级管理员使用
 */
@RestController
public class SystemApi {
    @Autowired
    UserService userService;
    /**
     * 查看所有管理员的信息：name、telephone、account、password、organizationName、date(创建时间)
     */
   /* public Object findAll(@RequestBody PageRequest pageRequest){

    }*/
}
