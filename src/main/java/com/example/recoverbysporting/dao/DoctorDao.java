package com.example.recoverbysporting.dao;

import com.example.recoverbysporting.entity.Doctor;
import com.example.recoverbysporting.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DoctorDao {
    Doctor getDoctorByUid(int uid);
    Role getRoleByRoleName(String roleName);
    Doctor checkUser(String account,String password);
    Doctor getUserByAccount(String account);
    List<Doctor> getDoctorList();
}
