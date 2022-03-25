package com.example.recoverbysporting.controller.Notice;


import com.example.recoverbysporting.entity.Notice;
import com.example.recoverbysporting.service.NoticeService;
import com.example.recoverbysporting.utils.ResultBody;
import com.example.recoverbysporting.utils.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice")
public class NoticeApi {
    @Autowired
    NoticeService noticeService;

    /**
     * 管理员分页查看公告 ：id  、title 、writer 、content 、date
     * @param pageRequest
     * @return
     */
    @RequestMapping(value = "/allforAdmin",method = RequestMethod.POST)
    public Object findAll(@RequestBody PageRequest pageRequest){
        return new ResultBody<>(true,200,noticeService.findPage(pageRequest));

    }

    /**
     * 用户查看公告：id  、title 、writer 、content 、date
     * @return
     */
    @RequestMapping(value = "/allforUser",method = RequestMethod.GET)
    public Object finduser(){
        return new ResultBody<>(true, 200, noticeService.findAll());
    }

    /**
     *管理员
     * 新增公告: title 、writer 、content 、date
     * @param notice
     * @return
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Object insert(@RequestBody Notice notice){
        noticeService.insert(notice);
        return new ResultBody<>(true,200,null);
    }

    /**
     * 管理员
     *  删除公告的信息：id  、title 、writer 、content 、date
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object delete(@RequestParam int id){
        if (id <= 0) {
            return new ResultBody<>(false, 501, "missing id");
        }
        noticeService.delete(id);
        return new ResultBody<>(true, 200, null);
    }

    /**
     * 管理员
     *  修改公告信息：id  、title 、writer 、content 、date
     * @param notice
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object update(@RequestBody Notice notice){
        if (notice.getId() <= 0) {
            return new ResultBody<>(false, 501, "missing id");
        }
        noticeService.update(notice);
        return new ResultBody<>(true, 200, null);
    }

}
