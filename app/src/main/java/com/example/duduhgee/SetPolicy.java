package com.example.duduhgee;

public class SetPolicy {
//    String appID = "https://192.168.0.2:443/ResgisterRequest.php";

    public class OperationHeader{
        Version upv;
        Operation op;
        public String appID;

    }

    public class Version{
        public int major = 1;
        public int minor = 1;
    }

    public enum Operation {
        Reg,
        Auth,
        Dereg
    }

}
