package com.dnyanesh.learn.crudjdbc.model.microservice.receiverobject;

public class ClpeksReceiver {
        
        String word;
        String Qs;
        String Qr;
        String PKs2;
        String PKr2;
        public ClpeksReceiver(String word, String qs, String qr, String pKs2, String pKr2) {
            this.word = word;
            Qs = qs;
            Qr = qr;
            PKs2 = pKs2;
            PKr2 = pKr2;
        }
        public ClpeksReceiver() {
        }
        public String getWord() {
            return word;
        }
        public void setWord(String word) {
            this.word = word;
        }
        public String getQs() {
            return Qs;
        }
        public void setQs(String qs) {
            Qs = qs;
        }
        public String getQr() {
            return Qr;
        }
        public void setQr(String qr) {
            Qr = qr;
        }
        public String getPKs2() {
            return PKs2;
        }
        public void setPKs2(String pKs2) {
            PKs2 = pKs2;
        }
        public String getPKr2() {
            return PKr2;
        }
        public void setPKr2(String pKr2) {
            PKr2 = pKr2;
        }
        @Override
        public String toString() {
            return "ClpeksReceiver [PKr2=" + PKr2 + ", PKs2=" + PKs2 + ", Qr=" + Qr + ", Qs=" + Qs + ", word=" + word
                    + "]";
        }
        
}
