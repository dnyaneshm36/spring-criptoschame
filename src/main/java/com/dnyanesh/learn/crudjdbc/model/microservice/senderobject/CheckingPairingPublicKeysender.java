package com.dnyanesh.learn.crudjdbc.model.microservice.senderobject;

public class CheckingPairingPublicKeysender {

            boolean  checked ;
            String  description;
            long  requiredTime;
            public CheckingPairingPublicKeysender() {
            }
            public CheckingPairingPublicKeysender(boolean checked, String description, long requiredTime) {
                this.checked = checked;
                this.description = description;
                this.requiredTime = requiredTime;
            }
            public boolean isChecked() {
                return checked;
            }
            public void setChecked(boolean checked) {
                this.checked = checked;
            }
            public String getDescription() {
                return description;
            }
            public void setDescription(String description) {
                this.description = description;
            }
            public long getRequiredTime() {
                return requiredTime;
            }
            public void setRequiredTime(long requiredTime) {
                this.requiredTime = requiredTime;
            }
            @Override
            public String toString() {
                return "CheckingPairingPublicKeysender [checked=" + checked + ", description=" + description
                        + ", requiredTime=" + requiredTime + "]";
            }
            
}
