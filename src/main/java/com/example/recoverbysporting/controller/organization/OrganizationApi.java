package com.example.recoverbysporting.controller.organization;

import com.example.recoverbysporting.service.OrganizationService;
import com.example.recoverbysporting.utils.ResultBody;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员对机构进行增删改查
 */
@RestController
@RequestMapping("/organization")
public class OrganizationApi {
    @Autowired
    OrganizationService organizationService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object getList(){
        return new ResultBody<>(true,200,organizationService.getList());
    }

}
