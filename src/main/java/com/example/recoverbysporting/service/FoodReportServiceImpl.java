package com.example.recoverbysporting.service;

import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.dao.*;
import com.example.recoverbysporting.entity.FoodReport;
import com.example.recoverbysporting.entity.Report;
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
public class FoodReportServiceImpl implements FoodReportService{
    @Autowired
    FoodReportDao foodReportDao;
    @Autowired
    PatientDao patientDao;
    @Autowired
    DoctorDao doctorDao;
    @Autowired
    FoodDao foodDao;
    public static Page page;
    @Override
    public PageResult findPageForDoctor(PageRequest pageRequest, List<Integer> pids, int did) {
        return PageUtil.getPageResult(getPageInfoForDoctor(pageRequest, pids,did),page);
    }

    @Override
    public PageResult findPageForAdmin(PageRequest pageRequest, List<Integer> pids) {
        return PageUtil.getPageResult(getPageInfoForAdmin(pageRequest, pids),page);
    }

    @Override
    public void delete(int id) {
        foodReportDao.delete(id);
    }

    @Override
    public JSONObject getById(int id) {
        JSONObject res = new JSONObject();
        FoodReport report = foodReportDao.getById(id);
        res.put("id",report.getId());
        res.put("patientName",patientDao.getPatientById(report.getPid()).getName());
        res.put("telephone",patientDao.getPatientById(report.getPid()).getTelephone());
        res.put("doctorName",doctorDao.getDoctorByUid(report.getDid()).getName());
        res.put("foodContent",foodDao.getById(report.getFid()).getContent());//获取饮食指导的内容
        res.put("content",report.getContent());
        res.put("reportDate",report.getReportDate());
        res.put("advice",report.getAdvice());
        return res;
    }

    @Override
    public void advice(int id, String advice) { foodReportDao.advice(id, advice);
    }

    private PageInfo<?> getPageInfoForDoctor(PageRequest pageRequest, List<Integer> pids, Integer did) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        //设置分页数据
        page = PageHelper.startPage(pageNum,pageSize);
        List<JSONObject> res = new ArrayList<>();
        for(FoodReport report : findForDoctor(pids, did)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",report.getId());
            jsonObject.put("patientName",patientDao.getPatientById(report.getPid()).getName());
            jsonObject.put("telephone",patientDao.getPatientById(report.getPid()).getTelephone());
            jsonObject.put("reportDate",report.getReportDate());
            jsonObject.put("advice",report.getAdvice());
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
        for(FoodReport report : findForAdmin(pids)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",report.getId());
            jsonObject.put("patientName",patientDao.getPatientById(report.getPid()).getName());
            jsonObject.put("telephone",patientDao.getPatientById(report.getPid()).getTelephone());
            jsonObject.put("doctorName",doctorDao.getDoctorByUid(report.getDid()).getName());
            jsonObject.put("reportDate",report.getReportDate());
            jsonObject.put("advice",report.getAdvice());
            res.add(jsonObject);
        }
        return new PageInfo<>(res);
    }
    private List<FoodReport> findForDoctor(List<Integer> pids, Integer did){
        List<FoodReport> res = new ArrayList<>();
        if(pids.isEmpty()){
            Integer pid = null;
            res.addAll(foodReportDao.findForDoctor(pid,did));
        }
        for(Integer pid : pids){
            res.addAll(foodReportDao.findForDoctor(pid,did));
        }
        return res;
    }

    private List<FoodReport> findForAdmin(List<Integer> pids){
        List<FoodReport> res = new ArrayList<>();
        if(pids.isEmpty()){
            Integer pid = null;
            res.addAll(foodReportDao.findForAdmin(pid));
        }
        for(Integer pid : pids){
            res.addAll(foodReportDao.findForAdmin(pid));
        }
        return res;
    }

}
