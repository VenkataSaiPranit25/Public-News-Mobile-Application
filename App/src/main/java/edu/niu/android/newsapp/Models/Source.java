package edu.niu.android.newsapp.Models;

import java.io.Serializable;

//Getters and Setters

public class Source implements Serializable {
    private String id = "";
    private String name = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
