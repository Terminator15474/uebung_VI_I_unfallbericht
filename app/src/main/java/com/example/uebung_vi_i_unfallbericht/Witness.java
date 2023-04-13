package com.example.uebung_vi_i_unfallbericht;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Witness implements Serializable {
    private static int id_counter = 0;

    private int id;
    private String firstname;
    private String lastname;
    private String street;
    private String city;
    private String phone;


    public Witness(String firstname, String lastname, String street, String city, String phone) {
        this.id = ++id_counter;
        this.firstname = firstname;
        this.lastname = lastname;
        this.street = street;
        this.city = city;
        this.phone = phone;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @NonNull
    @Override
    public String toString() {
        return this.firstname + " " + this.lastname;
    }
}
