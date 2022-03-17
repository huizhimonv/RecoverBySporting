package com.example.recoverbysporting.dao;

import com.example.recoverbysporting.entity.Prescribe;
import com.example.recoverbysporting.entity.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportDao {
    List<Report> findForDoctor(Integer pid, Integer did);
    List<Report> findForAdmin(Integer pid);
    void delete(int id);
}
