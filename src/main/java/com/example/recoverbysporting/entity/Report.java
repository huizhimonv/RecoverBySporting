package com.example.recoverbysporting.entity;

import lombok.Data;

@Data
public class Report {
    private int id;
    private int pid;
    private int did;
    private int aid;
    private String content;
    private String reportDate;
    private String advice;
}
