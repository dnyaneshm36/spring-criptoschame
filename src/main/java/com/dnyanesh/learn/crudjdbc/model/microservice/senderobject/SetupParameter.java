package com.dnyanesh.learn.crudjdbc.model.microservice.senderobject;

public class SetupParameter {
        String P;
        String master_key_lambda;
        String PKc;
        String SKc;
        long  time;
        public SetupParameter(String p, String master_key_lambda, String pKc, String sKc, long time) {
            super();
            this.P = p;
            this.master_key_lambda = master_key_lambda;
            this.PKc = pKc;
            this.SKc = sKc;
            this.time = time;
        }
        public SetupParameter() {
            super();
        }
        public String getP() {
            return P;
        }
        public void setP(String p) {
            P = p;
        }
        public String getMaster_key_lamda() {
            return master_key_lambda;
        }
        public void setMaster_key_lamda(String master_key_lambda) {
            this.master_key_lambda = master_key_lambda;
        }
        public String getPKc() {
            return PKc;
        }
        public void setPKc(String pKc) {
            PKc = pKc;
        }
        public String getSKc() {
            return SKc;
        }
        public void setSKc(String sKc) {
            SKc = sKc;
        }
        public long getTime() {
            return time;
        }
        public void setTime(long time) {
            this.time = time;
        }
        @Override
        public String toString() {
            return "SetupParameter [P=" + P + ", PKc=" + PKc + ", SKc=" + SKc + ", master_key_lambda=" + master_key_lambda
                    + ", time=" + time + "]";
        }
        
        
}
