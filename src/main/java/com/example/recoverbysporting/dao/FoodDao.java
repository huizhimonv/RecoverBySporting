package com.example.recoverbysporting.dao;

import com.example.recoverbysporting.entity.Disease;
import com.example.recoverbysporting.entity.Food;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FoodDao {
    List<Food> findForDoctor(Integer pid, Integer did);
    List<Food> findForAdmin(Integer pid);
    void insert(@Param("food") Food food);
    void update(@Param("food") Food food);
    void delete(int id);
    Food getById(int id);
}
