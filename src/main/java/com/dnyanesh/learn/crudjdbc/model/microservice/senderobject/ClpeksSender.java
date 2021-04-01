package com.dnyanesh.learn.crudjdbc.model.microservice.senderobject;

import java.math.BigInteger;

public class ClpeksSender {

            String Ui;
            BigInteger Vi;
            long requiredTime;
            public ClpeksSender(String ui, BigInteger vi, long requiredTime) {
                Ui = ui;
                Vi = vi;
                this.requiredTime = requiredTime;
            }
            public ClpeksSender() {
            }
            public String getUi() {
                return Ui;
            }
            public void setUi(String ui) {
                Ui = ui;
            }
            public BigInteger getVi() {
                return Vi;
            }
            public void setVi(BigInteger vi) {
                Vi = vi;
            }
            public long getRequiredTime() {
                return requiredTime;
            }
            public void setRequiredTime(long requiredTime) {
                this.requiredTime = requiredTime;
            }
            @Override
            public String toString() {
                return "ClpeksSender [Ui=" + Ui + ", Vi=" + Vi + ", requiredTime=" + requiredTime + "]";
            }
            
}   
