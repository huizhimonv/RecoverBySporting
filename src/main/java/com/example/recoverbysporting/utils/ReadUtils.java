package com.example.recoverbysporting.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 读取配置的工具类
 */
public class ReadUtils {

    /**
     * 读取shiro的拦截器的配置
     * @return shiroFilterMap
     */
    @SneakyThrows
    public static Map<String, String> getShiroFilterMap(){
        ClassPathResource classPathResource = new ClassPathResource("static/shiroFilter.json");
        InputStream inputStream = classPathResource.getInputStream();
        String newLine = System.getProperty("line.separator");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder();
        String line; boolean flag = false;
        while ((line = reader.readLine()) != null) {
            result.append(flag? newLine: "").append(line);
            flag = true;
        }
        String json = result.toString();
        JSONArray array = JSONArray.parseArray(json);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        for (Object o : array) {
            JSONObject tmp = (JSONObject) o;
            filterChainDefinitionMap.put(tmp.getString("url"),tmp.getString("permission"));

        }
        return filterChainDefinitionMap;
    }
}
