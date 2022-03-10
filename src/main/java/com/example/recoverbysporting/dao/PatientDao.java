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
}
