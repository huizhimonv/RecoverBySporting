package com.example.recoverbysporting.controller.upload;

import com.alibaba.fastjson.JSONObject;
import com.example.recoverbysporting.config.AppConfig;
import com.example.recoverbysporting.utils.ResultBody;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/upload")
public class uploadForWangApi {
    //这个注入配置文件，主要是因为本地的路径和服务器url路径需要动态配置，可以自己写死，也可以动态获取
    @Autowired
    AppConfig appConfig;

    @RequestMapping("/editor")
    @ResponseBody
    public Object editor(@RequestParam("file") MultipartFile file) {
        String fileName ="";
        if(!file.isEmpty()){
            //返回的是字节长度,1M=1024k=1048576字节 也就是if(fileSize<5*1048576)
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            if(StringUtils.isBlank(suffix)){
                return new ResultBody<>(false,501,"无法识别");
            }

            fileName = System.currentTimeMillis()+suffix;
            String saveFileName = appConfig.getFilepath()+"/actionContent/"+fileName;
            File dest = new File(saveFileName);
            if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            try {
                file.transferTo(dest); //保存文件
            } catch (Exception e) {
                e.printStackTrace();
                return new ResultBody<>(false,502,"上传失败"+e.getMessage());
            }
        }else {
            return new ResultBody<>(false,501,"上传出错");
        }
        String imgUrl=appConfig.getUrlpath()+"/actionContent/"+fileName;
        JSONObject res = new JSONObject();
        res.put("imageUrl",imgUrl);
        return new ResultBody<>(true,200,imgUrl);
    }
}
