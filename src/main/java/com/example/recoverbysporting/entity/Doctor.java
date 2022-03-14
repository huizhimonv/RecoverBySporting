package com.example.recoverbysporting.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class Doctor implements Serializable {
    private Integer id;
    private String name;
    private int oid;
    private String telephone;
    private String role;
    private String password;
    private String account;
    private String date;
    private boolean isDisable;
}
