package com.example.recoverbysporting.entity;

import lombok.Data;

@Data
public class Food {
    private Integer id;
    private Integer pid;
    private Integer did;
    private String content;//医生的提示可以放在这里
    private String date;
    private String reportDate;
}
