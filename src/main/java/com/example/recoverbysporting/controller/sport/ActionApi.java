package com.example.recoverbysporting.controller.sport;

import com.example.recoverbysporting.utils.ResultBody;
import com.example.recoverbysporting.utils.page.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/action")
public class ActionApi {
    @RequestMapping("/all")
    public Object all(@RequestBody PageRequest pageRequest){
        return new ResultBody<>(true,200,null);
    }
}
