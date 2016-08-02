package com.pump.smartbank.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class LoanItem implements Serializable {

    private String period;
    private double total;
    private double principal;
    private double interest;
    private double principalBalance;

    public LoanItem() {
    }

    public LoanItem(String period, double total, double principal, double interest, double principalBalance) {
        this.period = period;
        this.total = total;
        this.principal = principal;
        this.interest = interest;
        this.principalBalance = principalBalance;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPrincipal() {
        return principal;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getPrincipalBalance() {
        return principalBalance;
    }

    public void setPrincipalBalance(double principalBalance) {
        this.principalBalance = principalBalance;
    }
}
