package com.example.recoverbysporting.dao;

import com.example.recoverbysporting.entity.Doctor;
import com.example.recoverbysporting.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.w3c.dom.ranges.DocumentRange;

import java.util.List;

@Mapper
public interface DoctorDao {
    Doctor getDoctorByUid(int uid);
    Role getRoleByRoleName(String roleName);
    Doctor checkUser(String account,String password);
    Doctor getUserByAccount(String account);
    List<Doctor> getDoctorList();
    //系统管理模块
    List<Doctor> getDoctor();
    void reset(int id);
    void updateRole(String account,String role);
    void insert(@Param("doctor") Doctor doctor);
    void update(@Param("doctor") Doctor doctor);
    void deleteRole(int id,String role);
    void delete(int id);
    void disable(int id);
    void cancelDisable(int id);
    Doctor getById(int id);
}
