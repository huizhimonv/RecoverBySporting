package com.example.recoverbysporting.service;

import cn.hutool.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.entity.Report;
import com.example.recoverbysporting.utils.page.PageRequest;
import com.example.recoverbysporting.utils.page.PageResult;

import java.util.List;

public interface ReportService {
    PageResult findPageForDoctor(PageRequest pageRequest, List<Integer> pids, int did);
    PageResult findPageForAdmin(PageRequest pageRequest,List<Integer> pids);
    void delete(int id);
    JSONObject getById(int id);
    void advice(int id,String advice);
}
