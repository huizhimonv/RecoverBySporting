package com.example.recoverbysporting.service;

import com.example.recoverbysporting.entity.Log;
import com.example.recoverbysporting.utils.page.PageRequest;
import com.example.recoverbysporting.utils.page.PageResult;

public interface LogService {
    void insert(Log log);
    PageResult findPageByDid(PageRequest pageRequest, int did);
    void delete(int did);
}
