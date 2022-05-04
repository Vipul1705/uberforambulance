package com.example.cp.ambulancetracking_app.user;

import java.util.ArrayList;

public class ChargeListData {

    public static ArrayList<String> chrgeid,ambid,ambname,charges,date1;

    public static ArrayList<String> getChrgeid() {
        return chrgeid;
    }

    public static void setChrgeid(ArrayList<String> chrgeid) {
        ChargeListData.chrgeid = chrgeid;
    }

    public static ArrayList<String> getAmbid() {
        return ambid;
    }

    public static void setAmbid(ArrayList<String> ambid) {
        ChargeListData.ambid = ambid;
    }

    public static ArrayList<String> getAmbname() {
        return ambname;
    }

    public static void setAmbname(ArrayList<String> ambname) {
        ChargeListData.ambname = ambname;
    }

    public static ArrayList<String> getCharges() {
        return charges;
    }

    public static void setCharges(ArrayList<String> charges) {
        ChargeListData.charges = charges;
    }

    public static ArrayList<String> getDate1() {
        return date1;
    }

    public static void setDate1(ArrayList<String> date1) {
        ChargeListData.date1 = date1;
    }
}
