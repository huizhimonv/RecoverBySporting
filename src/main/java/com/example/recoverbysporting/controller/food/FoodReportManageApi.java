package com.example.recoverbysporting.controller.food;

import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.entity.Doctor;
import com.example.recoverbysporting.entity.Log;
import com.example.recoverbysporting.service.FoodReportService;
import com.example.recoverbysporting.service.LogService;
import com.example.recoverbysporting.service.PatientService;
import com.example.recoverbysporting.service.UserService;
import com.example.recoverbysporting.utils.ResultBody;
import com.example.recoverbysporting.utils.page.PageRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/food/report/manage")
public class FoodReportManageApi {
    @Autowired
    FoodReportService foodReportService;
    @Autowired
    UserService userService;
    @Autowired
    PatientService patientService;
    @Autowired
    LogService logService;


    /**
     * 医生
     *  分页查看饮食汇报（按照汇报时间降序排列） id、patientName、telephone、reportDate
     */
    @RequestMapping(value = "/allForDoctor",method = RequestMethod.GET)
    public Object findAll(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                          @RequestParam(required = false)String patientName){
        PageRequest pageRequest = new PageRequest(pageNum,pageSize);
        List<Integer> pids = new ArrayList<>();
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        //将pids为null分为两种情况
        if(patientName == null){
            return new ResultBody<>(true, 200, foodReportService.findPageForDoctor(pageRequest, pids, doctor.getId()));
        }
        pids = patientService.getIdByName(patientName);
        if(pids.isEmpty()){
            return new ResultBody<>(true,200,null);
        }
        return new ResultBody<>(true, 200, foodReportService.findPageForDoctor(pageRequest, pids, doctor.getId()));
    }

    /**管理员
     * 分页返回执行汇报（按照时间降序排列） id、patientName、telephone、doctorName、reportDate
     */
    @RequestMapping(value = "/allForAdmin",method = RequestMethod.GET)
    public Object findAllForAdmin(@RequestParam Integer pageNum,@RequestParam Integer pageSize,
                                  @RequestParam(required = false)String patientName){
        PageRequest pageRequest = new PageRequest(pageNum,pageSize);
        List<Integer> pids = new ArrayList<>();
        //将pids为null分为两种情况
        if(patientName == null){
            return new ResultBody<>(true, 200, foodReportService.findPageForAdmin(pageRequest, pids));
        }
        pids = patientService.getIdByName(patientName);
        if(pids.isEmpty()){
            return new ResultBody<>(true,200,null);
        }
        return new ResultBody<>(true, 200, foodReportService.findPageForAdmin(pageRequest, pids));
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
        if(isAdmin()){
            logService.insert(new Log(getIdAndDate().getDid(), "删除["+patientService.getById(id).getName()+"]的饮食汇报", getIdAndDate().getDate(), "成功"));
        }
        foodReportService.delete(id);
        return new ResultBody<>(true,200,null);
    }

    /**
     * 根据汇报的id返回具体的信息  id、patientName、doctorName、foodContent、content、reportDate、advice
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById",method = RequestMethod.GET)
    public Object getById(@RequestParam Integer id){
        if(id == null){
            return new ResultBody<>(false,501,"missing id");
        }
        return new ResultBody<>(true,200,foodReportService.getById(id));
    }

    /**
     * 医生或管理员根据id提建议
     * @return
     */
    public Object advice(@RequestBody JSONObject data){
        Integer id = data.getInteger("id");
        String advice = data.getString("advice");
        if(id == null){
            return new ResultBody<>(false,501,"missing id");
        }
        foodReportService.advice(id,advice);
        return new ResultBody<>(true,200,null);
    }

    private Log getIdAndDate(){
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());
        return new Log(doctor.getId(),date);
    }
    private  boolean isAdmin(){
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        if(doctor.getRole().contains("admin")){
            return true;
        }else {
            return false;
        }
    }
}
