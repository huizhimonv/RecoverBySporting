package com.example.recoverbysporting.controller.sport;

import com.example.recoverbysporting.entity.Action;
import com.example.recoverbysporting.service.ActionService;
import com.example.recoverbysporting.utils.ResultBody;
import com.example.recoverbysporting.utils.page.PageRequest;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/action/manage")
public class ActionManageApi {
    @Autowired
    ActionService actionService;

    /**
     * 医生或者管理员查看所有的动作库
     * @param pageRequest
     * @return
     */
    @RequestMapping(value = "/all",method = RequestMethod.POST)
    public Object all(@RequestBody PageRequest pageRequest){
        return new ResultBody<>(true,200,actionService.findPage(pageRequest));
    }
    /**
     * 医生或者管理员根据id查看具体的内容
     */
    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    public Object findById(@RequestParam Integer id){
        if(id == null){
            return new ResultBody<>(false,501,"missing id");
        }
        return new ResultBody<>(true,200,actionService.findById(id));
    }

    /**
     * 管理员新增动作库 name,type,content,date
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @RequiresRoles("admin")
    public Object insert(@RequestBody Action action){
        actionService.insert(action);
        return new ResultBody<>(true,200,null);
    }

    /**
     *管理员更新动作库 id,name,type,content,date
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @RequiresRoles("admin")
    public Object update(@RequestBody Action action){
        if(action.getId() == null){
            return new ResultBody<>(false,501,"missing id");
        }
        actionService.update(action);
        return new ResultBody<>(true,200,null);
    }
    /**
     * 管理员删除动作库
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @RequiresRoles("admin")
    public Object delete(@RequestParam Integer id){
        if(id == null){
            return new ResultBody<>(false,501,"missing id");
        }
        actionService.delete(id);
        return new ResultBody<>(true,200,null);
    }
    /**
     * 动作库下拉框
     */
    @RequestMapping(value = "/getList",method = RequestMethod.GET)
    public Object getList(){
        return new ResultBody<>(true,200,actionService.getList());
    }
}
