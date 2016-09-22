package com.pump.smartbank.domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by xu.nan on 2016/8/14.
 */
@Table(name = "terminal")
public class Terminal {
    @Column(name = "id", isId = true, autoGen = true)
    private int id;
    @Column(name = "terminalno")
    private String terminalno;
    @Column(name = "terminaltype")
    private String terminaltype;
    @Column(name = "subscribe")
    private boolean subscribe;

    public boolean isSubscribe() {
        return subscribe;
    }

    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTerminalno() {
        return terminalno;
    }

    public void setTerminalno(String terminalno) {
        this.terminalno = terminalno;
    }

    public String getTerminaltype() {
        return terminaltype;
    }

    public void setTerminaltype(String terminaltype) {
        this.terminaltype = terminaltype;
    }

}
