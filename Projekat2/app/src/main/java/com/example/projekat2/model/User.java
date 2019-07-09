package com.example.projekat2.model;

import com.google.firebase.database.Exclude;

import androidx.annotation.Nullable;

public class User {

    @Exclude
    public static final String indexRegEx = "[Rr][MmNnIi][1-9]?[1-9]-1[0-9]";

    @Exclude
    private String index;

    private String name;

    private String password;

    public User() {

    }

    public User(String index, String password,String name) {
        this.index = index;
        this.name = name;
        this.password = password;
    }


    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof User) {
            User u = (User) obj;
            return u.index.equals(index) && u.name.equals(name);
        }
        return super.equals(obj);
    }
}
