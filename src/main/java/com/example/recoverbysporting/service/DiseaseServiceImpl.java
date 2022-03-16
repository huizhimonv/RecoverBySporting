package com.example.recoverbysporting.service;

import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.dao.DiseaseDao;
import com.example.recoverbysporting.dao.DoctorDao;
import com.example.recoverbysporting.dao.PatientDao;
import com.example.recoverbysporting.entity.Disease;
import com.example.recoverbysporting.entity.Patient;
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
public class DiseaseServiceImpl implements DiseaseService{
    @Autowired
    DiseaseDao diseaseDao;
    @Autowired
    PatientDao patientDao;
    @Autowired
    DoctorDao doctorDao;
    public static Page page;

    @Override
    public List<JSONObject> findById(int pid, int did) {
        List<JSONObject> res = new ArrayList<>();
        for(Disease disease : diseaseDao.findById(pid, did)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",disease.getPid());
            jsonObject.put("patientName",patientDao.getPatientById(disease.getPid()).getName());
            jsonObject.put("sugar",disease.getSugar());
            jsonObject.put("sleep",disease.getSleep());
            jsonObject.put("joint",disease.getJoint());
            jsonObject.put("date",disease.getDate());
            res.add(jsonObject);
        }
        return res;
    }

    @Override
    public List<JSONObject> findByPid(int pid) {
        List<JSONObject> res = new ArrayList<>();
        for(Disease disease : diseaseDao.findByPid(pid)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",disease.getPid());
            jsonObject.put("patientName",patientDao.getPatientById(disease.getPid()).getName());
            jsonObject.put("doctorName",doctorDao.getDoctorByUid(disease.getDid()).getName());
            jsonObject.put("sugar",disease.getSugar());
            jsonObject.put("sleep",disease.getSleep());
            jsonObject.put("joint",disease.getJoint());
            jsonObject.put("date",disease.getDate());
            res.add(jsonObject);
        }
        return res;
    }

    @Override
    public PageResult findPageForDoctor(PageRequest pageRequest, List<Integer> pids, int did) {
        return PageUtil.getPageResult(getPageInfoForDoctor(pageRequest, pids,did),page);
    }

    @Override
    public PageResult findPageForAdmin(PageRequest pageRequest, List<Integer> pids) {
        return PageUtil.getPageResult(getPageInfoForAdmin(pageRequest, pids),page);
    }

    @Override
    public void insertForDoctor(Disease disease) {
        diseaseDao.insert(disease);
    }

    @Override
    public void insertForAdmin(Disease disease) {
        diseaseDao.insert(disease);
    }

    @Override
    public void update(Disease disease) {
        diseaseDao.update(disease);
    }

    @Override
    public void delete(int id) {
        diseaseDao.delete(id);
    }

    private PageInfo<?> getPageInfoForDoctor(PageRequest pageRequest,List<Integer> pids,Integer did) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        //设置分页数据
        page = PageHelper.startPage(pageNum,pageSize);
        List<JSONObject> res = new ArrayList<>();
        for(Disease disease : findForDoctor(pids, did)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",disease.getId());
            jsonObject.put("patientName",patientDao.getPatientById(disease.getPid()).getName());
            jsonObject.put("sugar",disease.getSugar());
            jsonObject.put("sleep",disease.getSleep());
            jsonObject.put("joint",disease.getJoint());
            jsonObject.put("date",disease.getDate());
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
        for(Disease disease : findForAdmin(pids)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",disease.getId());
            jsonObject.put("patientName",patientDao.getPatientById(disease.getPid()).getName());
            jsonObject.put("doctorName",doctorDao.getDoctorByUid(disease.getDid()).getName());
            jsonObject.put("sugar",disease.getSugar());
            jsonObject.put("sleep",disease.getSleep());
            jsonObject.put("joint",disease.getJoint());
            jsonObject.put("date",disease.getDate());
            res.add(jsonObject);
        }
        return new PageInfo<>(res);
    }

    private List<Disease> findForDoctor(List<Integer> pids,Integer did){
        List<Disease> res = new ArrayList<>();
        if(pids.isEmpty()){
            Integer pid = null;
            res.addAll(diseaseDao.findForDoctor(pid,did));
        }
        for(Integer pid : pids){
            res.addAll(diseaseDao.findForDoctor(pid,did));
        }
        return res;
    }

    private List<Disease> findForAdmin(List<Integer> pids){
        List<Disease> res = new ArrayList<>();
        if(pids.isEmpty()){
            Integer pid = null;
            res.addAll(diseaseDao.findForAdmin(pid));
        }
        for(Integer pid : pids){
            res.addAll(diseaseDao.findForAdmin(pid));
        }
        return res;
    }
}
