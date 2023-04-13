package com.example.uebung_vi_i_unfallbericht;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Witness implements Serializable {
    private static int id_counter = 0;

    private int id;
    private String firstname;
    private String lastname;
    private String street;
    private int city;
    private int plz;
    private String phone;


    public Witness(String firstname, String lastname, String street, int city, int plz, String phone) {
        this.id = ++id_counter;
        this.firstname = firstname;
        this.lastname = lastname;
        this.street = street;
        this.city = city;
        this.plz = plz;
        this.phone = phone;
    }

    public static int getId_counter() {
        return id_counter;
    }

    public static void setId_counter(int id_counter) {
        Witness.id_counter = id_counter;
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

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
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
