package com.dnyanesh.learn.crudjdbc.model;

import java.math.BigInteger;

import it.unisa.dia.gas.jpbc.Element;

public class CipherWord {

        Element Ui;
        BigInteger Vi;
        public CipherWord(Element ui, BigInteger vi) {
            super();
            this.Ui = ui.duplicate();
            this.Vi = vi;
        }
        public CipherWord() {
            super();
        }
        public Element getUi() {
            return Ui;
        }
        public void setUi(Element ui) {
            Ui = ui.duplicate();
        }
        public BigInteger getVi() {
            return Vi;
        }
        public void setVi(BigInteger vi) {
            Vi = vi;
        }
        @Override
        public String toString() {
            return "CipherWord [Ui=" + Ui + ", Vi=" + Vi + "]";
        }
        
}
