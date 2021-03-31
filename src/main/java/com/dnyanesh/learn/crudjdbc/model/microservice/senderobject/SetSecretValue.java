package com.dnyanesh.learn.crudjdbc.model.microservice.senderobject;

public class SetSecretValue {

        String desciption;
        String Su;
        long time;
        public SetSecretValue(String desciption, String su,long time) {
            this.desciption = desciption;
            this.Su = su;
            this.time = time;
        }
        public SetSecretValue() {
        }
        public String getDesciption() {
            return desciption;
        }
        public void setDesciption(String desciption) {
            this.desciption = desciption;
        }
        public String getSu() {
            return Su;
        }
        public void setSu(String su) {
            this.Su = su;
        }
        public long getTime() {
            return time;
        }
        public void setTime(long time) {
            this.time = time;
        }
        @Override
        public String toString() {
            return "SetSecretValue [Su=" + Su + ", desciption=" + desciption + ", time=" + time + "]";
        }
        
    
}
