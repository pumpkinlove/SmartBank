package com.pump.smartbank.domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by xu.nan on 2016/8/1.
 */
@Table(name="product")
public class Product implements Serializable {
    @Column(name = "id",isId = true,autoGen = true)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "content")
    private String content;     //产品描述
    @Column(name = "image")
    private String image;
    @Column(name = "term")
    private String term;         //产品营销话术

    public Product() {
    }

    public Product(String name, String content, String image, String term) {
        this.name = name;
        this.content = content;
        this.image = image;
        this.term = term;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
