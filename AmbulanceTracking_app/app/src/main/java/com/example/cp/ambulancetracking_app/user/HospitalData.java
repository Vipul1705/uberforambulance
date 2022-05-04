package com.example.cp.ambulancetracking_app.user;

import java.util.ArrayList;

public class HospitalData {

    public static ArrayList<String> hospid,hospname,email,cont,address,lat,lon;

    public static void setHospname(ArrayList<String> hospname) {
        HospitalData.hospname = hospname;
    }

    public static void setHospid(ArrayList<String> hospid) {
        HospitalData.hospid = hospid;
    }

    public static ArrayList<String> getAddress() {
        return address;
    }

    public static ArrayList<String> getCont() {
        return cont;
    }

    public static ArrayList<String> getEmail() {
        return email;
    }

    public static ArrayList<String> getHospid() {
        return hospid;
    }

    public static ArrayList<String> getHospname() {
        return hospname;
    }

    public static void setAddress(ArrayList<String> address) {
        HospitalData.address = address;
    }

    public static ArrayList<String> getLat() {
        return lat;
    }

    public static void setCont(ArrayList<String> cont) {
        HospitalData.cont = cont;
    }

    public static void setEmail(ArrayList<String> email) {
        HospitalData.email = email;
    }

    public static ArrayList<String> getLon() {
        return lon;
    }

    public static void setLat(ArrayList<String> lat) {
        HospitalData.lat = lat;
    }

    public static void setLon(ArrayList<String> lon) {
        HospitalData.lon = lon;
    }

}
