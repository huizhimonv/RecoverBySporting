package com.example.recoverbysporting.service;

import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.dao.LogDao;
import com.example.recoverbysporting.entity.Log;
import com.example.recoverbysporting.entity.Patient;
import com.example.recoverbysporting.utils.page.PageRequest;
import com.example.recoverbysporting.utils.page.PageResult;
import com.example.recoverbysporting.utils.page.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogServiceImpl implements LogService{
    @Autowired
    LogDao logDao;
    public static Page page;
    @Override
    public void insert(Log log) {
        logDao.insert(log);
    }

    @Override
    public PageResult findPageByDid(PageRequest pageRequest, int did) {
        return PageUtil.getPageResult(getPageInfoByDid(pageRequest,did),page);
    }

    @Override
    public void delete(int did) {
        logDao.delete(did);
    }


    private PageInfo<?> getPageInfoByDid(PageRequest pageRequest, int did) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        //设置分页数据
        page = PageHelper.startPage(pageNum,pageSize);
        List<JSONObject> res = new ArrayList<>();
        for(Log log : logDao.findByDid(did)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",log.getId());
            jsonObject.put("content",log.getContent());
            jsonObject.put("date",log.getDate());
            jsonObject.put("state",log.getState());
            res.add(jsonObject);
        }
        return new PageInfo<>(res);
    }
}
