package com.example.recoverbysporting.controller.food;

import com.example.recoverbysporting.entity.Doctor;
import com.example.recoverbysporting.entity.Food;
import com.example.recoverbysporting.service.FoodService;
import com.example.recoverbysporting.service.PatientService;
import com.example.recoverbysporting.service.UserService;
import com.example.recoverbysporting.utils.ResultBody;
import com.example.recoverbysporting.utils.page.PageRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/food/manage")
public class FoodManageApi {
    @Autowired
    FoodService foodService;
    @Autowired
    UserService userService;
    @Autowired
    PatientService patientService;
    /**
     * 医生：
     *    返回对应的所有患者的饮食指导（可搜索）id、patientName、content、date、reportDate
     *    （降序排列）
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
            return new ResultBody<>(true, 200, foodService.findPageForDoctor(pageRequest, pids, doctor.getId()));
        }
        pids = patientService.getIdByName(patientName);
        if(pids.isEmpty()){
            return new ResultBody<>(true,200,null);
        }
        return new ResultBody<>(true, 200, foodService.findPageForDoctor(pageRequest, pids, doctor.getId()));
    }
    /**
     * 管理员：
     *    返回所有患者的饮食指导（可搜索）id、patientName、doctorName、content、date、reportDate
     *    （降序排列）
     */
    @RequestMapping(value = "/allForAdmin",method = RequestMethod.GET)
    public Object allForAdmin(@RequestParam Integer pageNum,@RequestParam Integer pageSize,
                               @RequestParam(required = false) String patientName){
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        //根据patientName获得pid,因为存在不同的病人可能有相同的名字
        List<Integer> pids = new ArrayList<>();
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        //将pids为null分为两种情况
        if(patientName == null){
            return new ResultBody<>(true, 200, foodService.findPageForAdmin(pageRequest, pids));
        }
        pids = patientService.getIdByName(patientName);
        if(pids.isEmpty()){
            return new ResultBody<>(true,200,null);
        }
        return new ResultBody<>(true, 200, foodService.findPageForAdmin(pageRequest, pids));
    }
    /**
     * 医生：（点击新增后跳到一个新的页面）
     *   新增患者的饮食指导 ：pid、content、date
     */
    @RequestMapping("/insertForDoctor")
    public Object insertForDoctor(@RequestBody Food food){
        if(food.getPid() == null){
            return new ResultBody<>(false,501,"missing pid");
        }
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        food.setDid(doctor.getId());
        foodService.insert(food);
        return new ResultBody<>(true,200,null);
    }
    /**
     * 管理员：（点击新增后跳到一个新的页面）
     *   新增患者的饮食指导 ：did、pid、content、date
     */
    @RequestMapping("/insertForAdmin")
    public Object insertForAdmin(@RequestBody Food food){
        if(food.getPid() == null){
            return new ResultBody<>(false,501,"missing pid");
        }
        foodService.insert(food);
        return new ResultBody<>(true,200,null);
    }
    /**
     * 医生：
     *     修改患者的饮食指导 ：id、pid、content、date
     */
    @RequestMapping("/updateForDoctor")
    public Object updateForDoctor(@RequestBody Food food){
        if(food.getId() == null){
            return new ResultBody<>(false,501,"missing id");
        }
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        Doctor doctor = userService.getUserByAccount(account);
        food.setDid(doctor.getId());
        foodService.update(food);
        return new ResultBody<>(true,200,null);
    }
    /**
     * 管理员：
     *     修改患者的饮食指导 ：id、did、pid、content、date
     */
    @RequestMapping("/updateForAdmin")
    public Object updateForAdmin(@RequestBody Food food){
        if(food.getId() == null){
            return new ResultBody<>(false,501,"missing id");
        }
        foodService.update(food);
        return new ResultBody<>(true,200,null);
    }
    /**
     * 医生或管理员删除饮食指导
     */
    @RequestMapping("/delete")
    public Object delete(@RequestParam Integer id){
        if(id == null){
            return new ResultBody<>(false,501,"missing id");
        }
        foodService.delete(id);
        return new ResultBody<>(true,200,null);
    }
}
