package com.example.recoverbysporting.entity;

import lombok.Data;

@Data
public class Prescribe {
    private Integer id;
    private int pid;
    private int did;
    private int aid;
    private String date;//处方时间
    private String reportDate;//最近汇报时间
    private String tip;//医生的提示
    private String advice;//医生的建议（汇报建议）
}
