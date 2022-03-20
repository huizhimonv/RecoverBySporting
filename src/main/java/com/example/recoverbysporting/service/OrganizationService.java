package com.example.recoverbysporting.service;

import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.entity.Organization;

import java.util.List;

public interface OrganizationService {
    Organization findByOid(int oid);
    List<Organization> getList();
    List<JSONObject> findAll();
    void insert(Organization organization);
    void update(Organization organization);
    void delete(int id);
}
