package com.pump.smartbank.domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/31 0031.
 */
@Table(name="customer")
public class Customer implements Serializable {

    @Column(name = "id",isId = true, autoGen = true)
    private int id;
    @Column(name = "customname")
    private String customname;
    private String pingyin;
    private String sex;
    private String birthday;
    private String cardid;
    private String validate;
    private String carddept;
    private String address;
    private String telphone;
    private String mail;
    private String eveaddress;
    private String mailcode;
    private String opdate;
    private String optime;
    private String mz;
    private String workaddr;
    private String workcode;
    private String tel;
    private String worktel;
    private String business;
    private String idface;
    private String idback;
    private String cardpic;
    private String netpic;
    private String career;
    private String nation;
    @Column(name = "comeTime")
    private String comeTime;
    @Column(name = "comeDate")
    private String comeDate;


    public Customer() {
    }

    public String getCustomname() {
        return customname;
    }

    public void setCustomname(String customname) {
        this.customname = customname;
    }

    public String getPingyin() {
        return pingyin;
    }

    public void setPingyin(String pingyin) {
        this.pingyin = pingyin;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    public String getCarddept() {
        return carddept;
    }

    public void setCarddept(String carddept) {
        this.carddept = carddept;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getEveaddress() {
        return eveaddress;
    }

    public void setEveaddress(String eveaddress) {
        this.eveaddress = eveaddress;
    }

    public String getMailcode() {
        return mailcode;
    }

    public void setMailcode(String mailcode) {
        this.mailcode = mailcode;
    }

    public String getOpdate() {
        return opdate;
    }

    public void setOpdate(String opdate) {
        this.opdate = opdate;
    }

    public String getOptime() {
        return optime;
    }

    public void setOptime(String optime) {
        this.optime = optime;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getWorkaddr() {
        return workaddr;
    }

    public void setWorkaddr(String workaddr) {
        this.workaddr = workaddr;
    }

    public String getWorkcode() {
        return workcode;
    }

    public void setWorkcode(String workcode) {
        this.workcode = workcode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getWorktel() {
        return worktel;
    }

    public void setWorktel(String worktel) {
        this.worktel = worktel;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getIdface() {
        return idface;
    }

    public void setIdface(String idface) {
        this.idface = idface;
    }

    public String getIdback() {
        return idback;
    }

    public void setIdback(String idback) {
        this.idback = idback;
    }

    public String getCardpic() {
        return cardpic;
    }

    public void setCardpic(String cardpic) {
        this.cardpic = cardpic;
    }

    public String getNetpic() {
        return netpic;
    }

    public void setNetpic(String netpic) {
        this.netpic = netpic;
    }

    public String getComeTime() {
        return comeTime;
    }

    public void setComeTime(String comeTime) {
        this.comeTime = comeTime;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getComeDate() {
        return comeDate;
    }

    public void setComeDate(String comeDate) {
        this.comeDate = comeDate;
    }
}
