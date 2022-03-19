package com.example.recoverbysporting.service;

import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.dao.DoctorDao;
import com.example.recoverbysporting.dao.FoodDao;
import com.example.recoverbysporting.dao.PatientDao;
import com.example.recoverbysporting.entity.Disease;
import com.example.recoverbysporting.entity.Food;
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
public class FoodServiceImpl implements FoodService{
    @Autowired
    FoodDao foodDao;
    @Autowired
    DoctorDao doctorDao;
    @Autowired
    PatientDao patientDao;
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
    public void insert(Food food) {
        foodDao.insert(food);
    }

    @Override
    public void update(Food food) {
        foodDao.update(food);
    }

    @Override
    public void delete(int id) {
        foodDao.delete(id);
    }

    private PageInfo<?> getPageInfoForDoctor(PageRequest pageRequest, List<Integer> pids, Integer did) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        //设置分页数据
        page = PageHelper.startPage(pageNum,pageSize);
        List<JSONObject> res = new ArrayList<>();
        for(Food food : findForDoctor(pids, did)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",food.getId());
            jsonObject.put("patientName",patientDao.getPatientById(food.getPid()).getName());
            jsonObject.put("content",food.getContent());
            jsonObject.put("date",food.getDate());
            jsonObject.put("reportDate",food.getReportDate());
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
        for(Food food : findForAdmin(pids)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",food.getId());
            jsonObject.put("patientName",patientDao.getPatientById(food.getPid()).getName());
            jsonObject.put("doctorName",doctorDao.getDoctorByUid(food.getDid()).getName());
            jsonObject.put("content",food.getContent());
            jsonObject.put("date",food.getDate());
            jsonObject.put("reportDate",food.getReportDate());
            res.add(jsonObject);
        }
        return new PageInfo<>(res);
    }

    private List<Food> findForDoctor(List<Integer> pids,Integer did){
        List<Food> res = new ArrayList<>();
        if(pids.isEmpty()){
            Integer pid = null;
            res.addAll(foodDao.findForDoctor(pid,did));
        }
        for(Integer pid : pids){
            res.addAll(foodDao.findForDoctor(pid,did));
        }
        return res;
    }

    private List<Food> findForAdmin(List<Integer> pids){
        List<Food> res = new ArrayList<>();
        if(pids.isEmpty()){
            Integer pid = null;
            res.addAll(foodDao.findForAdmin(pid));
        }
        for(Integer pid : pids){
            res.addAll(foodDao.findForAdmin(pid));
        }
        return res;
    }
}
