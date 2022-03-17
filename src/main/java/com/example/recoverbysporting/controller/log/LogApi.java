package com.example.recoverbysporting.controller.log;

import com.example.recoverbysporting.entity.Doctor;
import com.example.recoverbysporting.service.LogService;
import com.example.recoverbysporting.service.UserService;
import com.example.recoverbysporting.utils.ResultBody;
import com.example.recoverbysporting.utils.page.PageRequest;
import com.example.recoverbysporting.utils.page.PageResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LogApi {
    @Autowired
    LogService logService;
    @Autowired
    UserService userService;

    /**
     * 查看某个管理员的日志
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/getAll")
    public Object getById(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        PageRequest pageRequest = new PageRequest(pageNum,pageSize);
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        return new ResultBody<>(true,200,logService.findPageByDid(pageRequest,userService.getUserByAccount(account).getId()));
    }

    @RequestMapping("/delete")
    public Object delete(){
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Integer did = userService.getUserByAccount(account).getId();
        if(did == null){
            return new ResultBody<>(false,501,"error account");
        }
        logService.delete(did);
        return new ResultBody<>(true,200,null);
    }
}
