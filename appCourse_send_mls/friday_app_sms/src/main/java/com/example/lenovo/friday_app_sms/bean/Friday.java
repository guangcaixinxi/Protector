package com.example.lenovo.friday_app_sms.bean;

import java.util.Date;

/**
 * Created by lenovo on 2015/10/20.
 */
public class Friday
{
    private int id;
    private String name;
    private String desc;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Friday(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
