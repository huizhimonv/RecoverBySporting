package com.example.recoverbysporting.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.dao.ActionDao;
import com.example.recoverbysporting.dao.DoctorDao;
import com.example.recoverbysporting.dao.PatientDao;
import com.example.recoverbysporting.dao.PrescribeDao;
import com.example.recoverbysporting.entity.Disease;
import com.example.recoverbysporting.entity.Prescribe;
import com.example.recoverbysporting.utils.page.PageRequest;
import com.example.recoverbysporting.utils.page.PageResult;
import com.example.recoverbysporting.utils.page.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrescribeServiceImpl implements PrescribeService{
    @Autowired
    PrescribeDao prescribeDao;
    @Autowired
    PatientDao patientDao;
    @Autowired
    ActionDao actionDao;
    @Autowired
    DoctorDao doctorDao;

    public static Page page;
    @Override
    public List<JSONObject> findById(int pid, int did) {
        List<JSONObject> res = new ArrayList<>();
        for(Prescribe prescribe : prescribeDao.findById(pid, did)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",prescribe.getId());
            jsonObject.put("pid",prescribe.getPid());
            jsonObject.put("did",prescribe.getDid());
            jsonObject.put("patientName",patientDao.getPatientById(prescribe.getPid()).getName());
            jsonObject.put("telephone",patientDao.getPatientById(prescribe.getPid()).getTelephone());
            jsonObject.put("actionName",actionDao.findById(prescribe.getAid()).getName());
            jsonObject.put("date",prescribe.getDate());
            jsonObject.put("reportDate",prescribe.getReportDate());
            res.add(jsonObject);
        }
        return res;
    }

    @Override
    public List<JSONObject> findByPid(int pid) {
        List<JSONObject> res = new ArrayList<>();
        for(Prescribe prescribe : prescribeDao.findByPid(pid)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",prescribe.getId());
            jsonObject.put("pid",prescribe.getPid());
            jsonObject.put("did",prescribe.getDid());
            jsonObject.put("doctorName",doctorDao.getDoctorByUid(prescribe.getDid()).getName());
            jsonObject.put("patientName",patientDao.getPatientById(prescribe.getPid()).getName());
            jsonObject.put("telephone",patientDao.getPatientById(prescribe.getPid()).getTelephone());
            jsonObject.put("actionName",actionDao.findById(prescribe.getAid()).getName());
            jsonObject.put("date",prescribe.getDate());
            jsonObject.put("reportDate",prescribe.getReportDate());
            res.add(jsonObject);
        }
        return res;
    }

    @Override
    public void insert(Prescribe prescribe) {
        prescribeDao.insert(prescribe);
    }

    @Override
    public void update(Prescribe prescribe) {
        prescribeDao.update(prescribe);
    }

    @Override
    public void delete(int id) {
        prescribeDao.delete(id);
    }

    @Override
    public PageResult findPageForDoctor(PageRequest pageRequest, List<Integer> pids, int did) {
        return PageUtil.getPageResult(getPageInfoForDoctor(pageRequest, pids,did),page);
    }

    @Override
    public PageResult findPageForAdmin(PageRequest pageRequest, List<Integer> pids) {
        return PageUtil.getPageResult(getPageInfoForAdmin(pageRequest, pids),page);
    }


    private PageInfo<?> getPageInfoForDoctor(PageRequest pageRequest, List<Integer> pids, Integer did) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        //设置分页数据
        page = PageHelper.startPage(pageNum,pageSize);
        List<JSONObject> res = new ArrayList<>();
        for(Prescribe prescribe : findForDoctor(pids, did)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",prescribe.getId());
            jsonObject.put("pid",prescribe.getPid());
            jsonObject.put("patientName",patientDao.getPatientById(prescribe.getPid()).getName());
            jsonObject.put("telephone",patientDao.getPatientById(prescribe.getPid()).getTelephone());
            jsonObject.put("actionName",actionDao.findById(prescribe.getAid()).getName());
            jsonObject.put("date",prescribe.getDate());
            jsonObject.put("reportDate",prescribe.getReportDate());
            res.add(jsonObject);
        }
        return new PageInfo<>(res);
    }

    private PageInfo<?> getPageInfoForAdmin(PageRequest pageRequest,List<Integer> pids) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        //设置分页数据
        page = PageHelper.startPage(pageNum,pageSize);
        List<JSONObject> res = new ArrayList<>();
        for(Prescribe prescribe : findForAdmin(pids)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",prescribe.getId());
            jsonObject.put("pid",prescribe.getPid());
            jsonObject.put("did",prescribe.getDid());
            jsonObject.put("doctorName",doctorDao.getDoctorByUid(prescribe.getDid()).getName());
            jsonObject.put("patientName",patientDao.getPatientById(prescribe.getPid()).getName());
            jsonObject.put("telephone",patientDao.getPatientById(prescribe.getPid()).getTelephone());
            jsonObject.put("actionName",actionDao.findById(prescribe.getAid()).getName());
            jsonObject.put("date",prescribe.getDate());
            jsonObject.put("reportDate",prescribe.getReportDate());
            res.add(jsonObject);
        }
        return new PageInfo<>(res);
    }
    private List<Prescribe> findForDoctor(List<Integer> pids,Integer did){
        List<Prescribe> res = new ArrayList<>();
        if(pids.isEmpty()){
            Integer pid = null;
            res.addAll(prescribeDao.findForDoctor(pid,did));
        }
        for(Integer pid : pids){
            res.addAll(prescribeDao.findForDoctor(pid,did));
        }
        return res;
    }

    private List<Prescribe> findForAdmin(List<Integer> pids){
        List<Prescribe> res = new ArrayList<>();
        if(pids.isEmpty()){
            Integer pid = null;
            res.addAll(prescribeDao.findForAdmin(pid));
        }
        for(Integer pid : pids){
            res.addAll(prescribeDao.findForAdmin(pid));
        }
        return res;
    }

}
