package com.example.recoverbysporting.controller.patient;

import com.example.recoverbysporting.entity.Doctor;
import com.example.recoverbysporting.entity.Patient;
import com.example.recoverbysporting.service.PatientService;
import com.example.recoverbysporting.service.UserService;
import com.example.recoverbysporting.utils.ResultBody;
import com.example.recoverbysporting.utils.page.PageRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient/manage")
public class PatientManageApi {
    @Autowired
    PatientService patientService;
    @Autowired
    UserService userService;

    /**
     * 管理员查看所有患者
     * @return
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/allForAdmin",method = RequestMethod.POST)
    public Object findAllForManager(@RequestBody PageRequest pageRequest){
        return new ResultBody<>(true,200,patientService.findPage(pageRequest));
    }

    /**
     * 医生查看自己的患者
     * @return
     */
    @RequestMapping(value = "/allForDoctor",method = RequestMethod.POST)
    public Object findAllForDoctor(@RequestBody PageRequest pageRequest){
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        System.out.println("---------"+doctor);
        return new ResultBody<>(true,200,patientService.findPageByDid(pageRequest,doctor.getId()));
    }

    /**
     * 医生添加患者
     * @param patient : name,telephone,sex,height,weight,birthday,oid(必选),startDate,endDate
     * @return
     */
    @RequestMapping(value = "/insertForDoctor",method = RequestMethod.POST)
    public Object insertByDoctor(@RequestBody Patient patient){
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        patient.setDid(doctor.getId());
        patientService.insert(patient);
        return new ResultBody<>(true,200,null);
    }

    /**
     * 返回所有医生的id、account、name、oid、organizationName
     * @return
     * 管理员新增患者的时候用
     */
    @RequestMapping(value = "/allDoctor")
    public Object findAllDoctor(){
        return new ResultBody<>(true,200,userService.getDoctorList());
    }
    /**
     * 管理员添加患者
     * @param patient: name,telephone,sex,height,weight,birthday,(oid,did)（必填）,startDate,endDate
     * @return
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/insertForAdmin",method = RequestMethod.POST)
    public Object insertByAdmin(@RequestBody Patient patient){
        patientService.insert(patient);
        return new ResultBody<>(true,200,null);
    }

    /**
     * 根据id返回对应患者的信息，包括did
     * @param id
     * @return
     */
    @RequestMapping(value = "/getPatientById",method = RequestMethod.GET)
    public Object getPatientById(int id){
        if(id <= 0){
            return new ResultBody<>(false,500,"error id");
        }
        return new ResultBody<>(true,200,patientService.getPatientById(id));
    }

    /**
     * 要注意，注册时间和最近登录时间是不能改变的
     * @param patient:id,name,telephone,sex,height,weight,birthday,oid(√),startDate,endDate
     * @return
     */
    @RequestMapping(value = "/updateForDoctor",method = RequestMethod.POST)
    public Object updateForDoctor(@RequestBody Patient patient){
        if(patient.getId() <= 0){
            return new ResultBody<>(false,500,"error id");
        }
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        patient.setDid(doctor.getId());
        patientService.update(patient);
        return new ResultBody<>(true,200,null);
    }

    /**
     *
     * @param patient id,name,telephone,sex,height,weight,birthday,oid,did,,startDate,endDate
     * @return
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/updateForAdmin",method = RequestMethod.POST)
    public Object updateForAdmin(@RequestBody Patient patient){
        if(patient.getId() <= 0){
            return new ResultBody<>(false,500,"error id");
        }
        patientService.update(patient);
        return new ResultBody<>(true,200,null);
    }

    /**
     * 根据id进行删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteById(int id){
        if(id <= 0){
            return new ResultBody<>(false,500,"error id");
        }
        patientService.deleteById(id);
        return new ResultBody<>(true,200,null);
    }
}
