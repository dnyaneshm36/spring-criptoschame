package com.dnyanesh.learn.crudjdbc.model.microservice.senderobject;

import java.util.Arrays;

public class TrapdoorSender {
    
            String T1;
            String T2;
            String T3;
            byte [] T2byte;
            byte [] T3byte;
            long requiredTime;
            public TrapdoorSender(String t1, String t2, String t3, byte[] t2byte, byte[] t3byte, long requiredTime) {
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
            public byte[] getT2byte() {
                return T2byte;
            }
            public void setT2byte(byte[] t2byte) {
                T2byte = t2byte;
            }
            public byte[] getT3byte() {
                return T3byte;
            }
            public void setT3byte(byte[] t3byte) {
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
                return "TrapdoorSender [T1=" + T1 + ", T2=" + T2 + ", T2byte=" + Arrays.toString(T2byte) + ", T3=" + T3
                        + ", T3byte=" + Arrays.toString(T3byte) + ", requiredTime=" + requiredTime + "]";
            }
            
            
}
