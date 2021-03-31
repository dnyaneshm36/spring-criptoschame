package com.dnyanesh.learn.crudjdbc.model.microservice.senderobject;

public class GeneratePublicKey {

            String PKu1;
            String PKu2;
            long requiredTime;
            public GeneratePublicKey(String pKu1, String pKu2, long requiredTime) {
                PKu1 = pKu1;
                PKu2 = pKu2;
                this.requiredTime = requiredTime;
            }
            public String getPKu1() {
                return PKu1;
            }
            public void setPKu1(String pKu1) {
                PKu1 = pKu1;
            }
            public String getPKu2() {
                return PKu2;
            }
            public void setPKu2(String pKu2) {
                PKu2 = pKu2;
            }
            public long getRequiredTime() {
                return requiredTime;
            }
            public void setRequiredTime(long requiredTime) {
                this.requiredTime = requiredTime;
            }
            @Override
            public String toString() {
                return "GeneratePublicKey [PKu1=" + PKu1 + ", PKu2=" + PKu2 + ", requiredTime=" + requiredTime + "]";
            }
            
}
