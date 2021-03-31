package com.dnyanesh.learn.crudjdbc.model.microservice.receiverobject;

public class GeneratePrivateKeyReceiver {
        String Su;
        String Du;
        
        public String getSu() {
                return Su;
        }
        public void setSu(String su) {
                Su = su;
        }
        public String getDu() {
                return Du;
        }
        public void setDu(String du) {
                Du = du;
        }
        public GeneratePrivateKeyReceiver(String su, String du) {
                Su = su;
                Du = du;
        }
        @Override
        public String toString() {
                return "GeneratePrivateKeyReceiver [Du=" + Du + ", Su=" + Su + "]";
        }
        
}
