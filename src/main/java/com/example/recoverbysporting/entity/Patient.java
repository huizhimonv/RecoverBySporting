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
    private Date birthday;
    private int oid;
    private int did;
    private String head;
    private Date startDate;
    private Date endDate;
    private Date registerDate;
    private Date loginDate;
}
