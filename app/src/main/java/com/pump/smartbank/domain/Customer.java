package com.pump.smartbank.domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/31 0031.
 */
@Table(name = "customer")
public class Customer implements Serializable {

    @Column(name = "id",isId = true,autoGen = true)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "nameSpell")
    private String nameSpell;
    @Column(name = "gender")
    private String gender;
    @Column(name = "birthday")
    private String birthday;
    @Column(name = "certificateType")
    private String certificateType;
    @Column(name = "certificate")
    private String certificate;
    @Column(name = "udCardStart")
    private String udCardStart;
    @Column(name = "idCardExpire")
    private String idCardExpire;
    @Column(name = "idCardDept")
    private String idCardDept;
    @Column(name = "address")
    private String address;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "eMail")
    private String eMail;
    @Column(name = "photo")
    private String photo;
    @Column(name = "age")
    private String age;
    @Column(name = "comeDate")
    private String comeDate;
    @Column(name = "comeTime")
    private String comeTime;


    public Customer() {
    }

    public Customer(String name, String comeTime, String comeDate) {
        this.name = name;
        this.comeTime = comeTime;
        this.comeDate = comeDate;
    }

    public String getComeDate() {
        return comeDate;
    }

    public void setComeDate(String comeDate) {
        this.comeDate = comeDate;
    }

    public String getComeTime() {
        return comeTime;
    }

    public void setComeTime(String comeTime) {
        this.comeTime = comeTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameSpell() {
        return nameSpell;
    }

    public void setNameSpell(String nameSpell) {
        this.nameSpell = nameSpell;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getUdCardStart() {
        return udCardStart;
    }

    public void setUdCardStart(String udCardStart) {
        this.udCardStart = udCardStart;
    }

    public String getIdCardExpire() {
        return idCardExpire;
    }

    public void setIdCardExpire(String idCardExpire) {
        this.idCardExpire = idCardExpire;
    }

    public String getIdCardDept() {
        return idCardDept;
    }

    public void setIdCardDept(String idCardDept) {
        this.idCardDept = idCardDept;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
