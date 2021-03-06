package com.example.recoverbysporting.service;

import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.entity.Prescribe;
import com.example.recoverbysporting.utils.page.PageRequest;
import com.example.recoverbysporting.utils.page.PageResult;

import java.util.List;

public interface PrescribeService {
    List<JSONObject> findById(int pid, int did);
    List<JSONObject> findByPid(int pid);
    void insert(Prescribe prescribe);
    void update(Prescribe prescribe);
    void delete(int id);
    PageResult findPageForDoctor(PageRequest pageRequest, List<Integer> pids, int did);
    PageResult findPageForAdmin(PageRequest pageRequest,List<Integer> pids);
}

