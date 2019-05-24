package com.material.components.waec;

public class User {
    private int id;
    private String phonenumber, verify_code;

    public User(int id, String phonenumber, String verify_code) {
        this.id = id;
        this.phonenumber = phonenumber;
        this.verify_code = verify_code;
    }

    public int getId() {
        return id;
    }

    public String getPhonenumber() {

        return phonenumber;
    }

    public String getVerify_code() {

        return verify_code;
    }

}