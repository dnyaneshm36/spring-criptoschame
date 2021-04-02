package com.dnyanesh.learn.crudjdbc.model.microservice.senderobject;

import java.util.ArrayList;

public class TrapdoorSender {
    
            String T1;
            String T2;
            String T3;
            ArrayList <Byte> T2byte;
            ArrayList <Byte> T3byte;
            long requiredTime;
            public TrapdoorSender(String t1, String t2, String t3, ArrayList<Byte> t2byte, ArrayList<Byte> t3byte,
                    long requiredTime) {
                T1 = t1;
                T2 = t2;
                T3 = t3;
                T2byte = t2byte;
                T3byte = t3byte;
                this.requiredTime = requiredTime;
            }
            public TrapdoorSender() {
            }
            public String getT1() {
                return T1;
            }
            public void setT1(String t1) {
                T1 = t1;
            }
            public String getT2() {
                return T2;
            }
            public void setT2(String t2) {
                T2 = t2;
            }
            public String getT3() {
                return T3;
            }
            public void setT3(String t3) {
                T3 = t3;
            }
            public ArrayList<Byte> getT2byte() {
                return T2byte;
            }
            public void setT2byte(ArrayList<Byte> t2byte) {
                T2byte = t2byte;
            }
            public ArrayList<Byte> getT3byte() {
                return T3byte;
            }
            public void setT3byte(ArrayList<Byte> t3byte) {
                T3byte = t3byte;
            }
            public long getRequiredTime() {
                return requiredTime;
            }
            public void setRequiredTime(long requiredTime) {
                this.requiredTime = requiredTime;
            }
            @Override
            public String toString() {
                return "TrapdoorSender [T1=" + T1 + ", T2=" + T2 + ", T2byte=" + T2byte + ", T3=" + T3 + ", T3byte="
                        + T3byte + ", requiredTime=" + requiredTime + "]";
            }
            
}
