package com.dnyanesh.learn.crudjdbc.model.microservice.receiverobject;

public class CheckingPairingPublicKey {
        String PKr1;
        String PKr2;
        public CheckingPairingPublicKey(String pKr1, String pKr2) {
            PKr1 = pKr1;
            PKr2 = pKr2;
        }
        public CheckingPairingPublicKey() {
        }
        public String getPKr1() {
            return PKr1;
        }
        public void setPKr1(String pKr1) {
            PKr1 = pKr1;
        }
        public String getPKr2() {
            return PKr2;
        }
        public void setPKr2(String pKr2) {
            PKr2 = pKr2;
        }
        @Override
        public String toString() {
            return "CheckingPairingPublicKey [PKr1=" + PKr1 + ", PKr2=" + PKr2 + "]";
        }
        
}
