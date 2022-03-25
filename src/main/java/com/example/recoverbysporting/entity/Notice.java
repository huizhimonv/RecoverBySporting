package com.example.recoverbysporting.entity;

import lombok.Data;

@Data
public class Notice {
    private  Integer id;
    private  String title;
    private  String writer;
    private  String content;
    private  String date;
}
