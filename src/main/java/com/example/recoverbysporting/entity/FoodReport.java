package com.example.recoverbysporting.entity;

import lombok.Data;

@Data
public class FoodReport {
    private int id;
    private int pid;
    private int did;
    private int fid;
    private String content;
    private String reportDate;
    private String advice;
}
