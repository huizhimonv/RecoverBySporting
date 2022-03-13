package com.example.recoverbysporting.dao;

import com.example.recoverbysporting.entity.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LogDao {
    void insert(@Param("log") Log log);
    List<Log> findByDid(int did);
    void delete(int did);
}
