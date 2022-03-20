package com.example.recoverbysporting.dao;

import com.example.recoverbysporting.entity.Organization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrganizationDao {
    //患者模块下使用
    Organization findByOid(int oid);
    List<Organization> getList();
    List<Organization> findAll();
    void insert(@Param("o")Organization organization);
    void update(@Param("o")Organization organization);
    void delete(int id);
}
