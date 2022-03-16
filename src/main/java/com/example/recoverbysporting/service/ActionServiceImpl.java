package com.example.recoverbysporting.service;

import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.dao.ActionDao;
import com.example.recoverbysporting.entity.Action;
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
public class ActionServiceImpl implements ActionService{
    @Autowired
    ActionDao actionDao;

    public static Page page;
    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtil.getPageResult(getPageInfo(pageRequest),page);
    }

    @Override
    public Action findById(int id) {
        return actionDao.findById(id);
    }

    @Override
    public void insert(Action action) {
        actionDao.insert(action);
    }

    @Override
    public void update(Action action) {
        actionDao.update(action);
    }

    @Override
    public void delete(int id) {
        actionDao.delete(id);
    }

    private PageInfo<?> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        //设置分页数据
        page = PageHelper.startPage(pageNum,pageSize);
        List<JSONObject> res = new ArrayList<>();
        for(Action action: actionDao.findAll()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",action.getId());
            jsonObject.put("name",action.getName());
            jsonObject.put("type",action.getType());
            jsonObject.put("date",action.getDate());
            res.add(jsonObject);
        }
        return new PageInfo<>(res);
    }
}
