package com.example.recoverbysporting.service;

import com.example.recoverbysporting.dao.OrganizationDao;
import com.example.recoverbysporting.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService{
    @Autowired
    OrganizationDao organizationDao;
    @Override
    public Organization findByOid(int oid) {
        return organizationDao.findByOid(oid);
    }

    @Override
    public List<Organization> getList() {
        return organizationDao.getList();
    }
}
