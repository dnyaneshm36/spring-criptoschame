package com.dnyanesh.learn.crudjdbc.model.microservice.senderobject;

public class PartialPrivateKey {

            String Qu;
            String Du;
            long requiredTime;
            public PartialPrivateKey(String qu, String du, long requiredTime) {
                this.Qu = qu;
                this.Du = du;
                this.requiredTime = requiredTime;
            }
            public String getQu() {
                return Qu;
            }
            public void setQu(String qu) {
                this.Qu = qu;
            }
            public String getDu() {
                return Du;
            }
            public void setDu(String du) {
                this.Du = du;
            }
            public long getRequiredTime() {
                return requiredTime;
            }
            public void setRequiredTime(long requiredTime) {
                this.requiredTime = requiredTime;
            }
            @Override
            public String toString() {
                return "PartialPrivateKey [Du=" + Du + ", Qu=" + Qu + ", requiredTime=" + requiredTime + "]";
            }
            
}
