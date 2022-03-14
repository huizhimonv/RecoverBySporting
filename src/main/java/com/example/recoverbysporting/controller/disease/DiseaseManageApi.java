package com.example.recoverbysporting.controller.disease;

import com.example.recoverbysporting.dao.DiseaseDao;
import com.example.recoverbysporting.entity.Disease;
import com.example.recoverbysporting.entity.Doctor;
import com.example.recoverbysporting.entity.Log;
import com.example.recoverbysporting.service.*;
import com.example.recoverbysporting.utils.ResultBody;
import com.example.recoverbysporting.utils.page.PageRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/disease/manage")
public class DiseaseManageApi {
    @Autowired
    DiseaseService diseaseService;
    @Autowired
    UserService userService;
    @Autowired
    PatientService patientService;
    @Autowired
    LogService logService;


    /**
     * 根据医生的id和病人的id 返回某一个患者的疾病汇报（patientName、sugar、sleep、joint、date）
     */
    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    public Object findById(@RequestParam Integer id) {
        if (id <= 0) {
            return new ResultBody<>(false, 501, "error id");
        }
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        return new ResultBody<>(true, 200, diseaseService.findById(id, doctor.getId()));
    }

    /**
     * 根据医生的id查找对应患者的所有疾病汇报（patientName、sugar、sleep、joint、date）
     * 具有搜索功能，通过病人的姓名 patientName要填完整
     */
    @RequestMapping(value = "/allForDoctor",method = RequestMethod.GET)
    public Object allForDoctor(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                               @RequestParam(required = false) String patientName) {
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        //根据patientName获得pid,因为存在不同的病人可能有相同的名字
        List<Integer> pids = patientService.getIdByName(patientName);
        if (pids == null) {
            return new ResultBody<>(false, 501, "unknown patientName");
        }
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        return new ResultBody<>(true, 200, diseaseService.findPageForDoctor(pageRequest, pids, doctor.getId()));
    }

    /**
     * 管理员：
     * 返回所有病人的疾病信息
     * 具有搜索功能，通过病人的姓名
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/allForAdmin",method = RequestMethod.GET)
    public Object allForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                              @RequestParam(required = false) String patientName) {
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        //根据patientName获得pid,因为存在不同的病人可能有相同的名字
        List<Integer> pids = patientService.getIdByName(patientName);
        if (pids == null) {
            if(isAdmin()){
                logService.insert(new Log(getIdAndDate().getDid(), "对患者进行搜索", getIdAndDate().getDate(), "失败"));
            }
            return new ResultBody<>(false, 501, "unknown patientName");
        }
        if(isAdmin()){
            logService.insert(new Log(getIdAndDate().getDid(), "查看患者的患病信息", getIdAndDate().getDate(), "成功"));
        }
        return new ResultBody<>(true,200,diseaseService.findPageForAdmin(pageRequest,pids));
    }

    /**
     * 医生：
     * 新增疾病汇报 pid,sugar、sleep、joint
     */
    @RequestMapping(value = "/insertForDoctor",method = RequestMethod.POST)
    public Object insertForDoctor(@RequestBody Disease disease){
        //获取当前的时间
        String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        disease.setDate(date);
        disease.setDid(doctor.getId());
        diseaseService.insertForDoctor(disease);
        return new ResultBody<>(true,200,null);
    }

    /**
     * 管理员：
     * 新增疾病汇报 did（下拉框）、pid、sugar、sleep、joint、
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/insertForAdmin",method = RequestMethod.POST)
    public Object insertForAdmin(@RequestBody Disease disease){
        //获取当前的时间
        String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        disease.setDate(date);
        diseaseService.insertForAdmin(disease);
        if(isAdmin()){
            logService.insert(new Log(getIdAndDate().getDid(), "新增疾病汇报", getIdAndDate().getDate(), "成功"));
        }
        return new ResultBody<>(true,200,null);
    }

    /**
     * 医生：
     * 修改疾病汇报 id(疾病的id),pid,sugar,sleep,joint
     */
    @RequestMapping(value = "/updateForDoctor",method = RequestMethod.POST)
    public Object updateForDoctor(@RequestBody Disease disease){
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        disease.setDid(doctor.getId());
        String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        disease.setDate(date);
        diseaseService.update(disease);
        return new ResultBody<>(true,200,null);
    }

    /**
     * 管理员：
     * 修改疾病汇报 id(疾病的id)、did、pid、sugar、sleep、joint
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/updateForAdmin",method = RequestMethod.POST)
    public Object updateForAdmin(@RequestBody Disease disease){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        disease.setDate(date);
        diseaseService.update(disease);
        if(isAdmin()){
            String name = patientService.getById(disease.getPid()).getName();
            logService.insert(new Log(getIdAndDate().getDid(), "对"+"["+name+"]"+"的疾病汇报进行更新操作", getIdAndDate().getDate(), "成功"));
        }
        return new ResultBody<>(true,200,null);
    }

    /**
     * 医生、管理员：
     * 对疾病进行删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object delete(@RequestParam int id){
        if(id <= 0){
            return new ResultBody<>(false,501,"error id");
        }
        diseaseService.delete(id);
        if(isAdmin()){
            logService.insert(new Log(getIdAndDate().getDid(), "对某个疾病汇报进行删除操作", getIdAndDate().getDate(), "成功"));
        }
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
