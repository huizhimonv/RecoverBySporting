package com.example.recoverbysporting.service;

import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.dao.DoctorDao;
import com.example.recoverbysporting.dao.OrganizationDao;
import com.example.recoverbysporting.dao.PatientDao;
import com.example.recoverbysporting.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrganizationServiceImpl implements OrganizationService{
    @Autowired
    OrganizationDao organizationDao;
    @Autowired
    DoctorDao doctorDao;
    @Autowired
    PatientDao patientDao;
    @Override
    public Organization findByOid(int oid) {
        return organizationDao.findByOid(oid);
    }

    @Override
    public List<Organization> getList() {
        return organizationDao.getList();
    }

    @Override
    public List<JSONObject> findAll() {
        List<JSONObject> res = new ArrayList<>();
        for(Organization organization : organizationDao.findAll()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",organization.getId());
            jsonObject.put("name",organization.getName());
            jsonObject.put("leader",organization.getLeader());
            jsonObject.put("date",organization.getDate());
            jsonObject.put("doctorCount",doctorDao.findByOid(organization.getId()).size());
            jsonObject.put("patientCount",patientDao.getByOid(organization.getId()).size());
            res.add(jsonObject);
        }
        return res;
    }

    @Override
    public void insert(Organization organization) {
        organizationDao.insert(organization);
    }

    @Override
    public void update(Organization organization) {
        organizationDao.update(organization);
    }

    @Override
    public void delete(int id) {
        organizationDao.delete(id);
    }
}
