package com.example.recoverbysporting.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Patient {
    private int id;
    private String name;
    private String telephone;
    private String sex;
    private double height;
    private double weight;
    private String birthday;
    private int oid;
    private int did;
    private String head;
    private String startDate;
    private String endDate;
    private String registerDate;
    private String loginDate;
    private String doctorName;
}
