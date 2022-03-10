package com.example.recoverbysporting.dao;

import com.example.recoverbysporting.entity.Disease;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiseaseDao {
    List<Disease> findById(Integer pid, Integer did);
    List<Disease> findForDoctor(Integer pid,Integer did);
    List<Disease> findForAdmin(Integer pid);
}
