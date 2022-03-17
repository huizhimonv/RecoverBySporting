package com.example.recoverbysporting.controller.sport;

import com.example.recoverbysporting.entity.Doctor;
import com.example.recoverbysporting.entity.Prescribe;
import com.example.recoverbysporting.service.PatientService;
import com.example.recoverbysporting.service.PrescribeService;
import com.example.recoverbysporting.service.UserService;
import com.example.recoverbysporting.utils.ResultBody;
import com.example.recoverbysporting.utils.page.PageRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/prescribe/manage")
public class PrescribeManegeApi {
    @Autowired
    PrescribeService prescribeService;
    @Autowired
    UserService userService;
    @Autowired
    PatientService patientService;
    /**
     * 医生：
     * 根据患者的id返回对应的运动处方 返回：pid、patientName、telephone、actionName、date，reportDate
     */
    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    public Object findById(@RequestParam Integer pid){
        if(pid == null){
            return new ResultBody<>(false,501,"missing id");
        }
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        return new ResultBody<>(true,200,prescribeService.findById(pid, doctor.getId()));
    }

    /**
     * 管理员：
     *   根据患者的id返回对应的运动处方 返回：patientName、doctorName、telephone、actionName、date，reportDate
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/findByPid",method = RequestMethod.GET)
    public Object findByPid(@RequestParam Integer pid){
        if(pid == null){
            return new ResultBody<>(false,501,"missing id");
        }
        return new ResultBody<>(true,200,prescribeService.findByPid(pid));
    }
    /**
     * 医生：
     *   返回医生对应的所有运动处方 id、patientName、telephone、actionName、date，reportDate
     *   具有搜索功能，通过病人的姓名
     */
    @RequestMapping(value = "/allForDoctor",method = RequestMethod.GET)
    public Object allForDoctor(@RequestParam Integer pageNum,@RequestParam Integer pageSize,
                               @RequestParam(required = false) String patientName){
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        //根据patientName获得pid,因为存在不同的病人可能有相同的名字
        List<Integer> pids = new ArrayList<>();
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        //将pids为null分为两种情况
        if(patientName == null){
            return new ResultBody<>(true, 200, prescribeService.findPageForDoctor(pageRequest, pids, doctor.getId()));
        }
        pids = patientService.getIdByName(patientName);
        if(pids.isEmpty()){
            return new ResultBody<>(true,200,null);
        }
        return new ResultBody<>(true, 200, prescribeService.findPageForDoctor(pageRequest, pids, doctor.getId()));
    }
    /**
     * 管理员：
     * 返回所有病人的运动处方
     * 具有搜索功能，通过病人的姓名
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/allForAdmin",method = RequestMethod.GET)
    public Object allForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                              @RequestParam(required = false) String patientName) {
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        //根据patientName获得pid,因为存在不同的病人可能有相同的名字
        List<Integer> pids = new ArrayList<>();
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        //将pids为null分为两种情况
        if(patientName == null){
            return new ResultBody<>(true, 200, prescribeService.findPageForDoctor(pageRequest, pids, doctor.getId()));
        }
        pids = patientService.getIdByName(patientName);
        if(pids.isEmpty()){
            return new ResultBody<>(true,200,null);
        }
        return new ResultBody<>(true, 200, prescribeService.findPageForDoctor(pageRequest, pids, doctor.getId()));
    }

    /**
     * 管理员：
     *   返回所有医生对应的所有运动处方 id、patientName、doctorName、telephone、actionName、date，reportDate
     */

    /**
     * 医生：
     *   新增患者的运动处方：pid、aid、tip(时间由后端补充)
     **/
    @RequestMapping(value = "/insertForDoctor",method = RequestMethod.POST)
    public Object insertForDoctor(@RequestBody Prescribe prescribe){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        prescribe.setDid(doctor.getId());
        prescribe.setDate(date);
        prescribeService.insert(prescribe);
        return new ResultBody<>(true,200,null);
    }
    /**
     * 管理员：
     *   新增患者的运动处方：pid、did、aid、tip(时间由后端补充)
     **/
    @RequiresRoles("admin")
    @RequestMapping(value = "/insertForAdmin",method = RequestMethod.POST)
    public Object insertForAdmin(@RequestBody Prescribe prescribe){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        prescribe.setDate(date);
        prescribeService.insert(prescribe);
        return new ResultBody<>(true,200,null);
    }
    /**
     * 医生：
     *   医生修改运动处方： id、pid、aid、tip
     */
    @RequestMapping(value = "/updateForDoctor",method = RequestMethod.POST)
    public Object updateForDoctor(@RequestBody Prescribe prescribe){
        if(prescribe.getId() == null){
            return new ResultBody<>(false,501,"missing id");
        }
        String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        prescribe.setDid(doctor.getId());
        prescribe.setDate(date);
        prescribeService.update(prescribe);
        return new ResultBody<>(true,200,null);
    }
    /**
     * 管理员：
     *   医生修改运动处方： id、pid、did、aid、tip
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/updateForAdmin",method = RequestMethod.POST)
    public Object updateForAdmin(@RequestBody Prescribe prescribe){
        if(prescribe.getId() == null){
            return new ResultBody<>(false,501,"missing id");
        }
        String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        prescribe.setDate(date);
        prescribeService.update(prescribe);
        return new ResultBody<>(true,200,null);
    }

    /**
     * 医生或管理员对运动处方进行删除操作
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object delete(@RequestParam Integer id){
        if(id == null){
            return new ResultBody<>(false,501,"missing id");
        }
        prescribeService.delete(id);
        return new ResultBody<>(true,200,null);
    }

}
