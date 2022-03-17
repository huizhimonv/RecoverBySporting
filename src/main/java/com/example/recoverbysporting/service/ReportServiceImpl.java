package com.example.recoverbysporting.service;

import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.dao.ActionDao;
import com.example.recoverbysporting.dao.DoctorDao;
import com.example.recoverbysporting.dao.PatientDao;
import com.example.recoverbysporting.dao.ReportDao;
import com.example.recoverbysporting.entity.Prescribe;
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
public class ReportServiceImpl implements ReportService{
    @Autowired
    ReportDao reportDao;
    @Autowired
    PatientDao patientDao;
    @Autowired
    ActionDao actionDao;
    @Autowired
    DoctorDao doctorDao;
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
        delete(id);
    }

    private PageInfo<?> getPageInfoForDoctor(PageRequest pageRequest, List<Integer> pids, Integer did) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        //设置分页数据
        page = PageHelper.startPage(pageNum,pageSize);
        List<JSONObject> res = new ArrayList<>();
        for(Report report : findForDoctor(pids, did)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",report.getId());
            jsonObject.put("patientName",patientDao.getPatientById(report.getPid()).getName());
            jsonObject.put("actionName",actionDao.findById(report.getAid()).getName());
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
        for(Report report : findForAdmin(pids)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",report.getId());
            jsonObject.put("patientName",patientDao.getPatientById(report.getPid()).getName());
            jsonObject.put("doctorName",doctorDao.getDoctorByUid(report.getDid()).getName());
            jsonObject.put("actionName",actionDao.findById(report.getAid()).getName());
            jsonObject.put("reportDate",report.getReportDate());
            jsonObject.put("advice",report.getAdvice());
            res.add(jsonObject);
        }
        return new PageInfo<>(res);
    }
    private List<Report> findForDoctor(List<Integer> pids, Integer did){
        List<Report> res = new ArrayList<>();
        if(pids.isEmpty()){
            Integer pid = null;
            res.addAll(reportDao.findForDoctor(pid,did));
        }
        for(Integer pid : pids){
            res.addAll(reportDao.findForDoctor(pid,did));
        }
        return res;
    }

    private List<Report> findForAdmin(List<Integer> pids){
        List<Report> res = new ArrayList<>();
        if(pids.isEmpty()){
            Integer pid = null;
            res.addAll(reportDao.findForAdmin(pid));
        }
        for(Integer pid : pids){
            res.addAll(reportDao.findForAdmin(pid));
        }
        return res;
    }

}
