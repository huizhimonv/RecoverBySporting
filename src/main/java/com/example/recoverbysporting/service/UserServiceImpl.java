package com.example.recoverbysporting.service;

import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.dao.DoctorDao;
import com.example.recoverbysporting.dao.OrganizationDao;
import com.example.recoverbysporting.entity.Doctor;
import com.example.recoverbysporting.entity.Patient;
import com.example.recoverbysporting.entity.Role;
import com.example.recoverbysporting.utils.page.PageRequest;
import com.example.recoverbysporting.utils.page.PageResult;
import com.example.recoverbysporting.utils.page.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    DoctorDao doctorDao;
    @Autowired
    OrganizationDao organizationDao;
    public static Page page;

    /**
     * 根据
     * @param uid
     * @return
     */
    @Override
    public List<String> getRoleByUid(int uid) {
        JSONObject role = (JSONObject) JSONObject.parse(doctorDao.getDoctorByUid(uid).getRole());
        if (role != null){
            return (List<String>) role.get("roles");
        }else {
            return null;
        }
    }

    @Override
    public List<String> getPermissionByUid(int uid) {
        List<String> permissions = new ArrayList<>();
        for(String roleName : getRoleByUid(uid)){
            //根据str（角色的名称）获取对应的权限
            Role role = doctorDao.getRoleByRoleName(roleName);
            JSONObject jsonObject = (JSONObject) JSONObject.parse(role.getPermissions());
            for(String permission : (List<String>)jsonObject.get("permissions")){
                permissions.add(permission);
            }
        }
        return permissions;
    }

    @Override
    public Doctor checkUser(String account, String password) {
        return doctorDao.checkUser(account, password);
    }

    @Override
    public Doctor getUserByAccount(String account) {
        return doctorDao.getUserByAccount(account);
    }

    @Override
    public List<JSONObject> getDoctorList() {
        List<JSONObject> res = new ArrayList<>();
        for(Doctor doctor : doctorDao.getDoctorList()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",doctor.getId());
            jsonObject.put("name",doctor.getName());
            jsonObject.put("oid",doctor.getOid());
            jsonObject.put("organizationName",organizationDao.findByOid(doctor.getOid()).getName());
            jsonObject.put("account",doctor.getAccount());
            res.add(jsonObject);
        }
        return res;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtil.getPageResult(getPageInfo(pageRequest),page);
    }

    @Override
    public void reset(int id) {
        doctorDao.reset(id);
    }

    @Override
    public void updateRole(String account) {
        String role = "{\"roles\":[\"staff\",\"admin\"]}";
        doctorDao.updateRole(account,role);
    }

    @Override
    public void insert(Doctor doctor) {
        doctorDao.insert(doctor);
    }

    @Override
    public void update(Doctor doctor) {
        doctorDao.update(doctor);
    }

    @Override
    public void deleteRole(int id) {
        String role = "{\"roles\":[\"staff\"]}";
        doctorDao.deleteRole(id,role);
    }

    @Override
    public void delete(int id) {
        doctorDao.delete(id);
    }

    @Override
    public void disable(int id) {
        doctorDao.disable(id);
    }

    @Override
    public void cancelDisable(int id) {
        doctorDao.cancelDisable(id);
    }

    @Override
    public Doctor getById(int id) {
        return doctorDao.getById(id);
    }

    /**
     * 查找所有的管理员
     * @param pageRequest
     * @return
     */
    private PageInfo<?> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        //设置分页数据
        page = PageHelper.startPage(pageNum,pageSize);
        List<JSONObject> res = new ArrayList<>();
        for(Doctor doctor : doctorDao.getAdmin()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",doctor.getId());
            jsonObject.put("name",doctor.getName());
            jsonObject.put("organizationName",organizationDao.findByOid(doctor.getOid()).getName());
            jsonObject.put("account",doctor.getAccount());
            jsonObject.put("password",doctor.getPassword());
            res.add(jsonObject);
        }
        return new PageInfo<>(res);
    }
}
