package com.example.recoverbysporting.service;

import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.utils.page.PageRequest;
import com.example.recoverbysporting.utils.page.PageResult;

import java.util.List;

public interface DiseaseService {
    List<JSONObject> findById(int pid,int did);
    PageResult findPageForDoctor(PageRequest pageRequest,List<Integer> pids,int did);
    PageResult findPageForAdmin(PageRequest pageRequest,List<Integer> pids);
}
