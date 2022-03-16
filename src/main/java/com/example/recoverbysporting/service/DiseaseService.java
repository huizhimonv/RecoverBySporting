package com.example.recoverbysporting.service;

import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.entity.Disease;
import com.example.recoverbysporting.utils.page.PageRequest;
import com.example.recoverbysporting.utils.page.PageResult;

import java.util.List;

public interface DiseaseService {
    List<JSONObject> findById(int pid,int did);
    List<JSONObject> findByPid(int pid);
    PageResult findPageForDoctor(PageRequest pageRequest,List<Integer> pids,int did);
    PageResult findPageForAdmin(PageRequest pageRequest,List<Integer> pids);
    void insertForDoctor(Disease disease);
    void insertForAdmin(Disease disease);
    void update(Disease disease);
    void delete(int id);
}
