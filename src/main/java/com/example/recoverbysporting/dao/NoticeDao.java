package com.example.recoverbysporting.dao;


import com.example.recoverbysporting.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeDao {
     List<Notice> findAll();
     void insert(@Param("notice") Notice notice);
     void update(@Param("notice") Notice notice);
     void delete(int id);
}
