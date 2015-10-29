package com.example.lenovo.friday_app_sms.bean;

/**
 * Created by lenovo on 2015/10/20.
 */
public class Msg {
    private int id;
    private int FridayId;
    private String content;

    public Msg(int id, int fridayId, String content) {
        this.id = id;
        FridayId = fridayId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFridayId() {
        return FridayId;
    }

    public void setFridayId(int fridayId) {
        FridayId = fridayId;
    }
}
