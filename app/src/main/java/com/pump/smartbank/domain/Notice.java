package com.pump.smartbank.domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xu.nan on 2016/7/28.
 * 提醒服务
 */
@Table(name = "notice")
public class Notice implements Serializable {
    @Column(name = "id",isId = true,autoGen = true )
    private int id;
    @Column( name = "content" )
    private String content;
    @Column( name = "date" )
    private String date;
    @Column( name = "time" )
    private String time;
    @Column( name = "position" )
    private String position;
    @Column( name = "type" )
    private int type;

    public Notice() {
    }

    public Notice(String content, String date, String time, String position, int type) {
        this.content = content;
        this.date = date;
        this.time = time;
        this.position = position;
        this.type = type;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

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
}
