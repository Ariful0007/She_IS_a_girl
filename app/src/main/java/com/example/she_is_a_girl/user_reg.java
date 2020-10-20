package com.example.she_is_a_girl;

public class user_reg {
    private  String  id;
    private String name;
    private  String password;
    private  String location;
    private String phonenumber;
    public user_reg()
    {

    }

    public user_reg(String id, String name, String password, String location, String phonenumber) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.location = location;
        this.phonenumber = phonenumber;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getLocation() {
        return location;
    }

    public String getPhonenumber() {
        return phonenumber;
    }
}
