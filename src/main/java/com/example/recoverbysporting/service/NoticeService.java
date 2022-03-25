package com.example.recoverbysporting.service;

import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.entity.Notice;
import com.example.recoverbysporting.utils.page.PageRequest;
import com.example.recoverbysporting.utils.page.PageResult;

import java.util.List;

public interface NoticeService {
    PageResult findPage(PageRequest pageRequest);
    List<JSONObject> findAll();
    void insert(Notice notice);
    void update(Notice notice);
    void delete(int id);


}
