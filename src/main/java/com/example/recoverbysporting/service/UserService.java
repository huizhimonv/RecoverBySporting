package com.example.recoverbysporting.service;

import com.example.recoverbysporting.entity.Doctor;

import java.util.List;

public interface UserService {
    List<String> getRoleByUid(int uid);
    List<String> getPermissionByUid(int uid);
    Doctor checkUser(String account,String password);
    Doctor getUserByAccount(String account);
}
