package com.example.recoverbysporting.service;

import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.entity.Doctor;
import com.example.recoverbysporting.utils.page.PageRequest;
import com.example.recoverbysporting.utils.page.PageResult;

import java.util.List;

public interface UserService {
    List<String> getRoleByUid(int uid);
    List<String> getPermissionByUid(int uid);
    Doctor checkUser(String account,String password);
    Doctor getUserByAccount(String account);
    List<JSONObject> getDoctorList();
    //系统管理模块
    PageResult findPage(PageRequest pageRequest);
    void reset(int id);
    void updateRole(String account);
    void insert(Doctor doctor);
    void update(Doctor doctor);
    void deleteRole(int id);
    void delete(int id);
    void disable(int id);
    void cancelDisable(int id);
    Doctor getById(int id);
}
