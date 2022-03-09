package com.example.recoverbysporting.controller.organization;

import com.example.recoverbysporting.service.OrganizationService;
import com.example.recoverbysporting.utils.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organization")
public class OrganizationApi {
    @Autowired
    OrganizationService organizationService;

    @RequestMapping("/list")
    public Object getList(){
        return new ResultBody<>(true,200,organizationService.getList());
    }
}
