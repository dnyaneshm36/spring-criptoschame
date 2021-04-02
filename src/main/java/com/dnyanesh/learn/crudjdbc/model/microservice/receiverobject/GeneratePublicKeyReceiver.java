package com.dnyanesh.learn.crudjdbc.model.microservice.receiverobject;

public class GeneratePublicKeyReceiver {
        String Su;

        public GeneratePublicKeyReceiver(String su) {
            Su = su;
        }

        public GeneratePublicKeyReceiver() {
        }

        public String getSu() {
            return Su;
        }

        public void setSu(String su) {
            Su = su;
        }

        @Override
        public String toString() {
            return "GeneratePublickeyReceiver [Su=" + Su + "]";
        }
        
}
