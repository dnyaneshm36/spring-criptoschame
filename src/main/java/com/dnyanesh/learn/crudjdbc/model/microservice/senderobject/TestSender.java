package com.dnyanesh.learn.crudjdbc.model.microservice.senderobject;

public class TestSender {
        
        boolean Test;
        String  descriptions;
        long requiredTime;
  
  
  
        public TestSender(boolean test, String descriptions, long requiredTime) {
            Test = test;
            this.descriptions = descriptions;
            this.requiredTime = requiredTime;
        }



        public TestSender() {
            super();
        }



        public boolean isTest() {
            return Test;
        }



        public void setTest(boolean test) {
            Test = test;
        }



        public String getDescriptions() {
            return descriptions;
        }



        public void setDescriptions(String descriptions) {
            this.descriptions = descriptions;
        }



        public long getRequiredTime() {
            return requiredTime;
        }



        public void setRequiredTime(long requiredTime) {
            this.requiredTime = requiredTime;
        }



        @Override
        public String toString() {
            return "TestSender [Test=" + Test + ", descriptions=" + descriptions + ", requiredTime=" + requiredTime
                    + "]";
        }
         
        
        
        



}
