package com.example.recoverbysporting.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Log {
    private int id;
    private int did;
    private String content;
    private String date;
    private String state;

    public Log(int did, String content, String date, String state) {
        this.did = did;
        this.content = content;
        this.date = date;
        this.state = state;
    }
    public Log(int did, String date) {
        this.did = did;
        this.date = date;
    }
}
