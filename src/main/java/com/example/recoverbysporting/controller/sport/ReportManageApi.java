package com.example.recoverbysporting.controller.sport;

import com.example.recoverbysporting.entity.Doctor;
import com.example.recoverbysporting.service.PatientService;
import com.example.recoverbysporting.service.ReportService;
import com.example.recoverbysporting.service.UserService;
import com.example.recoverbysporting.utils.ResultBody;
import com.example.recoverbysporting.utils.page.PageRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.omg.CORBA.INITIALIZE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/report/manage")
public class ReportManageApi {
    @Autowired
    PatientService patientService;
    @Autowired
    ReportService reportService;
    @Autowired
    UserService userService;

    /**医生
     * 分页返回执行汇报（按照时间降序排列） id、patientName、actionName、reportDate
     */
    @RequestMapping(value = "/allForDoctor",method = RequestMethod.GET)
    public Object findAll(@RequestParam Integer pageNum,@RequestParam Integer pageSize,
                          @RequestParam(required = false)String patientName){
        PageRequest pageRequest = new PageRequest(pageNum,pageSize);
        List<Integer> pids = new ArrayList<>();
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        //将pids为null分为两种情况
        if(patientName == null){
            return new ResultBody<>(true, 200, reportService.findPageForDoctor(pageRequest, pids, doctor.getId()));
        }
        pids = patientService.getIdByName(patientName);
        if(pids.isEmpty()){
            return new ResultBody<>(true,200,null);
        }
        return new ResultBody<>(true, 200, reportService.findPageForDoctor(pageRequest, pids, doctor.getId()));
    }

    /**管理员
     * 分页返回执行汇报（按照时间降序排列） id、patientName、doctorName、actionName、reportDate
     */
    @RequestMapping(value = "/allForAdmin",method = RequestMethod.GET)
    public Object findAllForAdmin(@RequestParam Integer pageNum,@RequestParam Integer pageSize,
                          @RequestParam(required = false)String patientName){
        PageRequest pageRequest = new PageRequest(pageNum,pageSize);
        List<Integer> pids = new ArrayList<>();
        //将pids为null分为两种情况
        if(patientName == null){
            return new ResultBody<>(true, 200, reportService.findPageForAdmin(pageRequest, pids));
        }
        pids = patientService.getIdByName(patientName);
        if(pids.isEmpty()){
            return new ResultBody<>(true,200,null);
        }
        return new ResultBody<>(true, 200, reportService.findPageForAdmin(pageRequest, pids));
    }
    /**
     * 医生或管理员
     * 根据执行汇报的id进行删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object delete(@RequestParam Integer id){
        if(id == null){
            return new ResultBody<>(false,501,"missing id");
        }
        reportService.delete(id);
        return new ResultBody<>(true,200,null);
    }

}
