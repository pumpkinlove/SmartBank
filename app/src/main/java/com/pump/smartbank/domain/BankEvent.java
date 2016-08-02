package com.pump.smartbank.domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by xu.nan on 2016/8/2.
 */
@Table(name="bankEvent")
public class BankEvent implements Serializable {
    @Column(name="id" ,isId=true, autoGen = true)
    private int id;
    @Column(name="name")
    private String name;
    @Column(name = "photo")
    private byte[] photo;
    @Column(name = "content")
    private String content;
    @Column(name = "bankName")
    private String bankName;

    public BankEvent() {
    }

    public BankEvent(String name, byte[] photo, String content, String bankName) {
        this.name = name;
        this.photo = photo;
        this.content = content;
        this.bankName = bankName;
    }

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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
