package com.example.recoverbysporting.entity;

import lombok.Data;

@Data
public class Doctor {
    private int id;
    private String name;
    private int oid;
    private String telephone;
    private String role;
    private String password;
    private String account;
}
