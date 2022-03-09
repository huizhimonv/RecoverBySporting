package com.example.recoverbysporting.service;

import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.dao.DoctorDao;
import com.example.recoverbysporting.entity.Doctor;
import com.example.recoverbysporting.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    DoctorDao doctorDao;

    /**
     * 根据
     * @param uid
     * @return
     */
    @Override
    public List<String> getRoleByUid(int uid) {
        JSONObject role = (JSONObject) JSONObject.parse(doctorDao.getDoctorByUid(uid).getRole());
        if (role != null){
            return (List<String>) role.get("roles");
        }else {
            return null;
        }
    }

    @Override
    public List<String> getPermissionByUid(int uid) {
        List<String> permissions = new ArrayList<>();
        for(String roleName : getRoleByUid(uid)){
            //根据str（角色的名称）获取对应的权限
            Role role = doctorDao.getRoleByRoleName(roleName);
            JSONObject jsonObject = (JSONObject) JSONObject.parse(role.getPermissions());
            for(String permission : (List<String>)jsonObject.get("permissions")){
                permissions.add(permission);
            }
        }
        return permissions;
    }

    @Override
    public Doctor checkUser(String account, String password) {
        return doctorDao.checkUser(account, password);
    }

    @Override
    public Doctor getUserByAccount(String account) {
        return doctorDao.getUserByAccount(account);
    }
}
