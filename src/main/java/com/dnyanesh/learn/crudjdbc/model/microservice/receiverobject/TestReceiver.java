package com.dnyanesh.learn.crudjdbc.model.microservice.receiverobject;

import java.util.ArrayList;


public class TestReceiver {

        ArrayList <Byte> T2byte;
        ArrayList <Byte> T3byte;
        String T1;
        String SKs1;
        String SKs2;

        ArrayList<CipherWordReceiver> encypteWords;

        public TestReceiver(ArrayList<Byte> t2byte, ArrayList<Byte> t3byte, String t1, String sKs1, String sKs2,
                ArrayList<CipherWordReceiver> encypteWords) {
            T2byte = t2byte;
            T3byte = t3byte;
            T1 = t1;
            SKs1 = sKs1;
            SKs2 = sKs2;
            this.encypteWords = encypteWords;
        }

        public TestReceiver() {
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

        public String getT1() {
            return T1;
        }

        public void setT1(String t1) {
            T1 = t1;
        }

        public String getSKs1() {
            return SKs1;
        }

        public void setSKs1(String sKs1) {
            SKs1 = sKs1;
        }

        public String getSKs2() {
            return SKs2;
        }

        public void setSKs2(String sKs2) {
            SKs2 = sKs2;
        }

        public ArrayList<CipherWordReceiver> getEncypteWords() {
            return encypteWords;
        }

        public void setEncypteWords(ArrayList<CipherWordReceiver> encypteWords) {
            this.encypteWords = encypteWords;
        }

        @Override
        public String toString() {
            return "TestReceiver [SKs1=" + SKs1 + ", SKs2=" + SKs2 + ", T1=" + T1 + ", T2byte=" + T2byte + ", T3byte="
                    + T3byte + ", encypteWords=" + encypteWords + "]";
        }

        
 }
