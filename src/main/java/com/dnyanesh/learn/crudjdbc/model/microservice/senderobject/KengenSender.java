package com.dnyanesh.learn.crudjdbc.model.microservice.senderobject;

public class KengenSender {
    String sks;
    String pks;
    long time ;
    public KengenSender() {
        super();
    }
    public KengenSender(String sks, String pks, long time) {
        this.sks = sks;
        this.pks = pks;
        this.time = time;
    }
    public String getSks() {
        return sks;
    }
    public void setSks(String sks) {
        this.sks = sks;
    }
    public String getPks() {
        return pks;
    }
    public void setPks(String pks) {
        this.pks = pks;
    }
    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    @Override
    public String toString() {
        return "KengenSender [pks=" + pks + ", sks=" + sks + ", requiredTime =" + time + "]";
    }


    
}