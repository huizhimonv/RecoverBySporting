package com.example.recoverbysporting.service;

import com.example.recoverbysporting.entity.Food;
import com.example.recoverbysporting.utils.page.PageRequest;
import com.example.recoverbysporting.utils.page.PageResult;

import java.util.List;

public interface FoodService {
    PageResult findPageForDoctor(PageRequest pageRequest, List<Integer> pids, int did);
    PageResult findPageForAdmin(PageRequest pageRequest,List<Integer> pids);
    void insert(Food food);
    void update(Food food);
    void delete(int id);
}
