package com.example.recoverbysporting.dao;

import com.example.recoverbysporting.entity.FoodReport;
import com.example.recoverbysporting.entity.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoodReportDao {
    List<FoodReport> findForDoctor(Integer pid, Integer did);
    List<FoodReport> findForAdmin(Integer pid);
    void delete(int id);
    FoodReport getById(int id);
    void advice(int id,String advice);
}
