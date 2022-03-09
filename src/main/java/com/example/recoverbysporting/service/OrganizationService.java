package com.example.recoverbysporting.service;

import com.example.recoverbysporting.entity.Organization;

import java.util.List;

public interface OrganizationService {
    Organization findByOid(int oid);
    List<Organization> getList();
}
