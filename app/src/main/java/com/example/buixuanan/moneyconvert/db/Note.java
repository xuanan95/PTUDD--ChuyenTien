package com.example.buixuanan.moneyconvert.db;

import java.io.Serializable;


public class Note implements Serializable {
    private int Id;
    private String date;
    private String price;

    public Note(int Id, String date, String price){
        this.Id = Id;
        this.date = date;
        this.price = price;
    }
    public Note(){}

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
