package com.example.recoverbysporting.dao;

import com.example.recoverbysporting.entity.Patient;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PatientDao {
    List<Patient> findAll();
}
