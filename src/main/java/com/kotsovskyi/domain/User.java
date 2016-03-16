package com.kotsovskyi.domain;

public class User {
    public User(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    private String name;
    private String password;
}
