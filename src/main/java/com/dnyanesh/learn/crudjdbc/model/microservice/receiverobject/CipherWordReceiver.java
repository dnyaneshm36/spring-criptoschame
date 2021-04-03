package com.dnyanesh.learn.crudjdbc.model.microservice.receiverobject;

import java.math.BigInteger;

public class CipherWordReceiver {
            String Ui;
            BigInteger Vi;
            long requiredTime;
            public CipherWordReceiver(String ui, BigInteger vi, long requiredTime) {
                Ui = ui;
                Vi = vi;
                this.requiredTime = requiredTime;
            }
            public CipherWordReceiver() {
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
                return "CipherWordReceiver [Ui=" + Ui + ", Vi=" + Vi + ", requiredTime=" + requiredTime + "]";
            }
            
}


    