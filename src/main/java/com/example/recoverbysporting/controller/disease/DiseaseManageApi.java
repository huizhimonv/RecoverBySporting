package com.example.recoverbysporting.controller.disease;

import com.example.recoverbysporting.dao.DiseaseDao;
import com.example.recoverbysporting.entity.Doctor;
import com.example.recoverbysporting.service.DiseaseService;
import com.example.recoverbysporting.service.PatientService;
import com.example.recoverbysporting.service.UserService;
import com.example.recoverbysporting.utils.ResultBody;
import com.example.recoverbysporting.utils.page.PageRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


    /**
     * 根据医生的id和病人的id 返回某一个患者的疾病汇报（patientName、sugar、sleep、joint、date）
     */
    @RequestMapping("/findById")
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
    @RequestMapping("/allForDoctor")
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
    @RequestMapping("/allForAdmin")
    public Object allForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                              @RequestParam(required = false) String patientName) {
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        //根据patientName获得pid,因为存在不同的病人可能有相同的名字
        List<Integer> pids = patientService.getIdByName(patientName);
        if (pids == null) {
            return new ResultBody<>(false, 501, "unknown patientName");
        }
        return new ResultBody<>(true,200,diseaseService.findPageForAdmin(pageRequest,pids));
    }

    /**
     * 医生：
     * 新增疾病汇报 did、sugar、sleep、joint
     */
    @RequestMapping("/insertForDoctor")
    public Object insertForDoctor(){
        return new ResultBody<>(true,200,null);
    }
}
