package com.example.recoverbysporting.dao;

import com.example.recoverbysporting.entity.Patient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PatientDao {
    List<Patient> findAll();
    List<Patient> findByDid(int did);
    void insert(@Param("patient") Patient patient);
    Patient getPatientById(int id);
    void update(@Param("patient") Patient patient);
    void deleteById(int id);
    //疾病汇报模块
    List<Integer> getIdByName(String patientName);
    //首页模块
    //饮食模块
    List<Patient> getListByDid(int did);
    List<Patient> getList();
    //机构管理模块
    List<Patient> getByOid(int oid);

}
