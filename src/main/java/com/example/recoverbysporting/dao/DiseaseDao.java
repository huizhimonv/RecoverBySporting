package com.example.recoverbysporting.dao;

import com.example.recoverbysporting.entity.Disease;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiseaseDao {
    List<Disease> findById(Integer pid, Integer did);
    List<Disease> findByPid(int pid);
    List<Disease> findForDoctor(Integer pid,Integer did);
    List<Disease> findForAdmin(Integer pid);
    void insert(@Param("disease") Disease disease);
    void update(@Param("disease")Disease disease);
    void delete(int id);
}
