package com.example.recoverbysporting.dao;

import com.example.recoverbysporting.entity.Organization;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrganizationDao {
    //患者模块下使用
    Organization findByOid(int oid);
    List<Organization> getList();
}
