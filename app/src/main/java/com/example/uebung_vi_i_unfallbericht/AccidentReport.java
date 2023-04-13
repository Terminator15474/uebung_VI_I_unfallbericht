package com.example.uebung_vi_i_unfallbericht;

import java.io.Serializable;
import java.util.Date;

public class AccidentReport implements Serializable {

    private static int counter = 0;

    private int id; //automatic
    private Date datetime;
    private String ort;
    private int plz;
    private String street;
    private int nr;
    private boolean injured, damage;

    public AccidentReport(Date datetime, String ort, int plz, String street, int nr, boolean injured, boolean damage) {
        this.id = ++counter;
        this.datetime = datetime;
        this.ort = ort;
        this.plz = plz;
        this.street = street;
        this.nr = nr;
        this.injured = injured;
        this.damage = damage;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        AccidentReport.counter = counter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public boolean isInjured() {
        return injured;
    }

    public void setInjured(boolean injured) {
        this.injured = injured;
    }

    public boolean isDamage() {
        return damage;
    }

    public void setDamage(boolean damage) {
        this.damage = damage;
    }
}
