package com.example.recoverbysporting.service;

import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.dao.DoctorDao;
import com.example.recoverbysporting.dao.OrganizationDao;
import com.example.recoverbysporting.dao.PatientDao;
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
public class PatientServiceImpl implements PatientService{
    @Autowired
    PatientDao patientDao;
    @Autowired
    OrganizationDao organizationDao;
    @Autowired
    DoctorDao doctorDao;
    public static Page page;
    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtil.getPageResult(getPageInfo(pageRequest),page);
    }

    @Override
    public PageResult findPageByDid(PageRequest pageRequest,int did) {
        return PageUtil.getPageResult(getPageInfoByDid(pageRequest,did),page);
    }

    @Override
    public void insert(Patient patient) {
        patientDao.insert(patient);
    }

    @Override
    public JSONObject getPatientById(int id) {
        JSONObject jsonObject = new JSONObject();
        Patient patient = patientDao.getPatientById(id);
        jsonObject.put("id",patient.getId());
        jsonObject.put("name",patient.getName());
        jsonObject.put("telephone",patient.getTelephone());
        jsonObject.put("sex",patient.getSex());
        jsonObject.put("height",patient.getHeight());
        jsonObject.put("weight",patient.getWeight());
        jsonObject.put("birthday",patient.getBirthday());
        jsonObject.put("startDate",patient.getStartDate());
        jsonObject.put("endDate",patient.getEndDate());
        jsonObject.put("registerDate",patient.getRegisterDate());
        jsonObject.put("organizationName",organizationDao.findByOid(patient.getOid()).getName());
        jsonObject.put("did",patient.getDid());
        jsonObject.put("doctorName",doctorDao.getDoctorByUid(patient.getDid()).getName());
        jsonObject.put("loginDate",patient.getLoginDate());
        return jsonObject;
    }

    @Override
    public void update(Patient patient) {
        patientDao.update(patient);
    }

    @Override
    public void deleteById(int id) {
        patientDao.deleteById(id);
    }

    @Override
    public List<Integer> getIdByName(String patientName) {
        return patientDao.getIdByName(patientName);
    }

    @Override
    public Patient getById(int id) {
        return patientDao.getPatientById(id);
    }

    private PageInfo<?> getPageInfoByDid(PageRequest pageRequest,int did) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        //设置分页数据
        page = PageHelper.startPage(pageNum,pageSize);
        List<JSONObject> res = new ArrayList<>();
        for(Patient patient : patientDao.findByDid(did)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",patient.getId());
            jsonObject.put("name",patient.getName());
            jsonObject.put("telephone",patient.getTelephone());
            jsonObject.put("sex",patient.getSex());
            jsonObject.put("height",patient.getHeight());
            jsonObject.put("weight",patient.getWeight());
            jsonObject.put("birthday",patient.getBirthday());
            jsonObject.put("startDate",patient.getStartDate());
            jsonObject.put("endDate",patient.getEndDate());
            jsonObject.put("registerDate",patient.getRegisterDate());
            jsonObject.put("oid",patient.getOid());
            jsonObject.put("organization",organizationDao.findByOid(patient.getOid()).getName());
            jsonObject.put("loginDate",patient.getLoginDate());
            res.add(jsonObject);
        }
        return new PageInfo<>(res);
    }

    private PageInfo<?> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        //设置分页数据
        page = PageHelper.startPage(pageNum,pageSize);
        List<JSONObject> res = new ArrayList<>();
        for(Patient patient : patientDao.findAll()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",patient.getId());
            jsonObject.put("name",patient.getName());
            jsonObject.put("telephone",patient.getTelephone());
            jsonObject.put("sex",patient.getSex());
            jsonObject.put("height",patient.getHeight());
            jsonObject.put("weight",patient.getWeight());
            jsonObject.put("birthday",patient.getBirthday());
            jsonObject.put("startDate",patient.getStartDate());
            jsonObject.put("endDate",patient.getEndDate());
            jsonObject.put("registerDate",patient.getRegisterDate());
            jsonObject.put("did",patient.getDid());
            jsonObject.put("doctorName",doctorDao.getDoctorByUid(patient.getDid()).getName());
            jsonObject.put("oid",patient.getOid());
            jsonObject.put("organization",organizationDao.findByOid(patient.getOid()).getName());
            jsonObject.put("loginDate",patient.getLoginDate());
            res.add(jsonObject);
        }
        return new PageInfo<>(res);
    }

}
