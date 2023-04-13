package com.example.uebung_vi_i_unfallbericht;

import android.net.wifi.rtt.WifiRttManager;

import java.io.Serializable;
import java.util.LinkedList;

public class AccidentReport implements Serializable {

    private static int counter = 0;

    private int id; //automatic
    private String date;
    private String time;
    private String ort;
    private int plz;
    private String street;
    private int nr;
    private boolean injured, damage;

    private LinkedList<Witness> witnesses = new LinkedList<>();

    public AccidentReport(int id, String date, String time, String ort, int plz, String street, int nr, boolean injured, boolean damage) {
        this.id = id;
        this.date = date;
        this.time = time;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public void addWitness(Witness witness) {
        witnesses.add(witness);
    }

    public void removeWitness(Witness witness) {
        witnesses.remove(witness);
    }

    public Witness getWitness(int id) {
        for (Witness witness : witnesses) {
            if (witness.getId() == id) {
                return witness;
            }
        }
        return null;
    }
}
