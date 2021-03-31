package com.dnyanesh.learn.crudjdbc.model.microservice.senderobject;

public class GeneratePrivateKey {

        String SKu1;
        String SKu2;
        long requiredTime;
        public GeneratePrivateKey(String sKu1, String sKu2,long requiredTime) {
            SKu1 = sKu1;
            SKu2 = sKu2;
            this.requiredTime = requiredTime;
        }
        public String getSKu1() {
            return SKu1;
        }
        public void setSKu1(String sKu1) {
            SKu1 = sKu1;
        }
        public String getSKu2() {
            return SKu2;
        }
        public void setSKu2(String sKu2) {
            SKu2 = sKu2;
        }
        @Override
        public String toString() {
            return "GeneratePrivateKey [SKu1=" + SKu1 + ", SKu2=" + SKu2 + ", requiredTime=" + requiredTime + "]";
        }
        public long getRequiredTime() {
            return requiredTime;
        }
        public void setRequiredTime(long requiredTime) {
            this.requiredTime = requiredTime;
        }
        
}
