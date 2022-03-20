package com.example.recoverbysporting.controller.organization;

import com.example.recoverbysporting.entity.Organization;
import com.example.recoverbysporting.service.OrganizationService;
import com.example.recoverbysporting.utils.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员对机构进行增删改查
 */
@RestController
@RequestMapping("/organization")
public class OrganizationApi {
    @Autowired
    OrganizationService organizationService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object getList() {
        return new ResultBody<>(true, 200, organizationService.getList());
    }

    /**
     * 管理员：
     * 返回所有的机构  id、name、leader、doctorCount、patientCount、date
     *
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Object findAll() {
        return new ResultBody<>(true, 200, organizationService.findAll());
    }

    /**
     * 管理员：
     * 新增机构 name、leader、telephone、date
     *
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@RequestBody Organization organization) {
        organizationService.insert(organization);
        return new ResultBody<>(true, 200, null);
    }

    /**
     * 管理员：
     * 修改机构信息：id、name、leader、telephone、date
     *
     * @param organization
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody Organization organization) {
        if (organization.getId() <= 0) {
            return new ResultBody<>(false, 501, "missing id");
        }
        organizationService.update(organization);
        return new ResultBody<>(true, 200, null);
    }

    /**
     * 管理员:
     * 删除机构信息：id、name、leader、telephone、date
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object delete(@RequestParam int id) {
        if (id <= 0) {
            return new ResultBody<>(false, 501, "missing id");
        }
        organizationService.delete(id);
        return new ResultBody<>(true, 200, null);
    }
}
