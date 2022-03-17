package com.example.recoverbysporting.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "system") // 接收application.yml中的myProps下面的属性
public class AppConfig {
    public String filepath;
    public String urlpath;
}