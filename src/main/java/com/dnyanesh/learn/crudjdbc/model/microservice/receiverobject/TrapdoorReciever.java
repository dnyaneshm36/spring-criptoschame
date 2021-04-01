package com.dnyanesh.learn.crudjdbc.model.microservice.receiverobject;

public class TrapdoorReciever {
        String SKr2;
        String PKs1;
        public TrapdoorReciever(String sKr2, String pKs1) {
            SKr2 = sKr2;
            PKs1 = pKs1;
        }
        public TrapdoorReciever() {
        }
        public String getSKr2() {
            return SKr2;
        }
        public void setSKr2(String sKr2) {
            SKr2 = sKr2;
        }
        public String getPKs1() {
            return PKs1;
        }
        public void setPKs1(String pKs1) {
            PKs1 = pKs1;
        }
        @Override
        public String toString() {
            return "TrapdoorReciever [PKs1=" + PKs1 + ", SKr2=" + SKr2 + "]";
        }
        
}
