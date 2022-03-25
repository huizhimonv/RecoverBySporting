package com.example.recoverbysporting.service;

import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.dao.NoticeDao;
import com.example.recoverbysporting.entity.Notice;
import com.example.recoverbysporting.entity.Organization;
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
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    NoticeDao noticeDao;

    public static Page page;
    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtil.getPageResult(getPageInfo(pageRequest),page);
    }

    @Override
    public List<JSONObject> findAll() {
        List<JSONObject> res = new ArrayList<>();
        for(Notice notice : noticeDao.findAll()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",notice.getId());
            jsonObject.put("title",notice.getTitle());
            jsonObject.put("write",notice.getWriter());
            jsonObject.put("content",notice.getContent());
            jsonObject.put("date",notice.getDate());
            res.add(jsonObject);
        }
        return res;
    }

    @Override
    public void insert(Notice notice) {
        noticeDao.insert(notice);
    }

    @Override
    public void update(Notice notice) {
        noticeDao.update(notice);
    }

    @Override
    public void delete(int id) {
        noticeDao.delete(id);
    }

    private PageInfo<?> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        //设置分页数据
        page = PageHelper.startPage(pageNum,pageSize);
        List<JSONObject> res = new ArrayList<>();
        for(Notice notice : noticeDao.findAll()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",notice.getId());
            jsonObject.put("title",notice.getTitle());
            jsonObject.put("writer",notice.getWriter());
            jsonObject.put("content",notice.getContent());
            jsonObject.put("date",notice.getDate());
            res.add(jsonObject);
        }
        return new PageInfo<>(res);
    }


}
