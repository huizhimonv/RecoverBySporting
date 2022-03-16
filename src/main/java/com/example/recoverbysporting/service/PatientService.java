package com.example.recoverbysporting.service;

import cn.hutool.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.entity.Patient;
import com.example.recoverbysporting.utils.page.PageRequest;
import com.example.recoverbysporting.utils.page.PageResult;

import java.util.List;

public interface PatientService {
   /* List<JSONObject> findAll();
    List<JSONObject> findByDid(int did);*/
    PageResult findPage(PageRequest pageRequest);
    PageResult findPageByDid(PageRequest pageRequest,int did);
    void insert(Patient patient);
    JSONObject getPatientById(int id);
    void update(Patient patient);
    void deleteById(int id);
    //疾病汇报模块
    List<Integer> getIdByName(String patientName);
    //系统管理模块
    Patient getById(int id);
}
