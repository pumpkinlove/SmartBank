package com.pump.smartbank.domain;

import java.io.Serializable;

public class Publish implements Serializable{
	private long pubid;
	private String opdate;
	private String optime;
	private String pubtype;
	private String objid;
	private String objname;
	private String terminalid;
	private String isread;
	public long getPubid() {
		return pubid;
	}
	public void setPubid(long pubid) {
		this.pubid = pubid;
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
	public String getPubtype() {
		return pubtype;
	}
	public void setPubtype(String pubtype) {
		this.pubtype = pubtype;
	}
	public String getObjid() {
		return objid;
	}
	public void setObjid(String objid) {
		this.objid = objid;
	}
	public String getObjname() {
		return objname;
	}
	public void setObjname(String objname) {
		this.objname = objname;
	}
	public String getTerminalid() {
		return terminalid;
	}
	public void setTerminalid(String terminalid) {
		this.terminalid = terminalid;
	}
	public String getIsread() {
		return isread;
	}
	public void setIsread(String isread) {
		this.isread = isread;
	}

	@Override
	public String toString() {
		return "Publish{" +
				"pubid=" + pubid +
				", opdate='" + opdate + '\'' +
				", optime='" + optime + '\'' +
				", pubtype='" + pubtype + '\'' +
				", objid='" + objid + '\'' +
				", objname='" + objname + '\'' +
				", terminalid='" + terminalid + '\'' +
				", isread='" + isread + '\'' +
				'}';
	}
}
