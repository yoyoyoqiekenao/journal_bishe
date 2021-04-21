package com.example.jorunal_bishe.dao;

import android.util.Log;

/**
 * @author : 徐无敌
 * date   : 2021/4/2017:25
 * desc   :
 */
public class TbNote {
    public static final String ID = "_id";
    public static final String DATE = "date";
    public static final String CONTENT = "content";
    public static final String TYPE = "type";

    public Long id;
    //创建时间
    public String date;
    //备忘录内容
    public String content;

    public int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TbNote{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                '}';
    }
}
