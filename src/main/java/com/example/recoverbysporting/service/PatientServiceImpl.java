package com.example.recoverbysporting.service;

import com.example.recoverbysporting.dao.PatientDao;
import com.example.recoverbysporting.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PatientServiceImpl implements PatientService{
    @Autowired
    PatientDao patientDao;
    @Override
    public List<Patient> findAll() {
        return patientDao.findAll();
    }
}
