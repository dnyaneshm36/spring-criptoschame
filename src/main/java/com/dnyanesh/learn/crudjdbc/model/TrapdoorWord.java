package com.dnyanesh.learn.crudjdbc.model;

import it.unisa.dia.gas.jpbc.Element;

public class TrapdoorWord {
        Element T1;
        Element T2;
        Element T3;
        String stringT2;
        String stringT3;
        public TrapdoorWord(Element t1, Element t2, Element t3, String stringT2, String stringT3) {
            super();
            this.T1 = t1.duplicate();
            this.T2 = t2.duplicate();
            this.T3 = t3.duplicate();
            this.stringT2 = stringT2;
            this.stringT3 = stringT3;
        }
        public TrapdoorWord() {
            super();
        }
        public Element getT1() {
            return T1;
        }
        public void setT1(Element t1) {
            this.T1 = t1.duplicate();
        }
        public Element getT2() {
            return T2;
        }
        public void setT2(Element t2) {
            this.T2 = t2.duplicate();
        }
        public Element getT3() {
            return T3;
        }
        public void setT3(Element t3) {
            this.T3 = t3.duplicate();
        }
        public String getStringT2() {
            return stringT2;
        }
        public void setStringT2(String stringT2) {
            this.stringT2 = stringT2;
        }
        public String getStringT3() {
            return stringT3;
        }
        public void setStringT3(String stringT3) {
            this.stringT3 = stringT3;
        }
        @Override
        public String toString() {
            return "TrapdoorWord [T1=" + T1 + ", T2=" + T2 + ", T3=" + T3 + ", stringT2=" + stringT2 + ", stringT3="
                    + stringT3 + "]";
        }
        
        
}
