package com.example.recoverbysporting.dao;

import com.example.recoverbysporting.entity.Action;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActionDao {
    List<Action> findAll();
    Action findById(int id);
    void insert(@Param("action")Action action);
    void update(@Param("action")Action action);
    void delete(int id);
}
