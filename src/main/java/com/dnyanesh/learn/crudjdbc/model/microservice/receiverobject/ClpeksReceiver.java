package com.dnyanesh.learn.crudjdbc.model.microservice.receiverobject;

import java.util.ArrayList;

public class ClpeksReceiver {
        
        ArrayList<String> words;
        String Qs;
        String Qr;
        String PKs2;
        String PKr2;
        public ClpeksReceiver(ArrayList<String> words, String qs, String qr, String pKs2, String pKr2) {
            this.words = words;
            Qs = qs;
            Qr = qr;
            PKs2 = pKs2;
            PKr2 = pKr2;
        }
        public ClpeksReceiver() {
        }
        public ArrayList<String> getWords() {
            return words;
        }
        public void setWords(ArrayList<String> words) {
            this.words = words;
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
            return "ClpeksReceiver [PKr2=" + PKr2 + ", PKs2=" + PKs2 + ", Qr=" + Qr + ", Qs=" + Qs + ", words=" + words
                    + "]";
        }
        
        
}
