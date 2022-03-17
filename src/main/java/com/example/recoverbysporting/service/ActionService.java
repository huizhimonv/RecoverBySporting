package com.example.recoverbysporting.service;

import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.entity.Action;
import com.example.recoverbysporting.utils.page.PageRequest;
import com.example.recoverbysporting.utils.page.PageResult;

import java.util.List;

public interface ActionService {
    PageResult findPage(PageRequest pageRequest);
    Action findById(int id);
    void insert(Action action);
    void update(Action action);
    void delete(int id);
    List<JSONObject> getList();
}
