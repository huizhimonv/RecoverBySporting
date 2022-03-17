package com.example.recoverbysporting.dao;

import com.example.recoverbysporting.entity.Disease;
import com.example.recoverbysporting.entity.Prescribe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PrescribeDao {
    List<Prescribe> findById(int pid, int did);
    List<Prescribe> findByPid(int pid);
    void insert(@Param("prescribe") Prescribe prescribe);
    void update(@Param("prescribe") Prescribe prescribe);
    void delete(int id);
    List<Prescribe> findForDoctor(Integer pid, Integer did);
    List<Prescribe> findForAdmin(Integer pid);
}
