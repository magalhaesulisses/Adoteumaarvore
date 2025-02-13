package com.example.tccadoteumaarvore.model;

import java.util.Date;

public class Informativo {
    private String country;
    private Date date;
    private String text;


    public String getCountry() { return country; }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
