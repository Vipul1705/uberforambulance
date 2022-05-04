package com.example.cp.ambulancetracking_app.ambulance;

import java.util.ArrayList;

public class UserReqData {

    public static ArrayList<String> bookid,userid,contact,date;

    public static ArrayList<String> getBookid() {
        return bookid;
    }

    public static ArrayList<String> getContact() {
        return contact;
    }

    public static ArrayList<String> getDate() {
        return date;
    }

    public static ArrayList<String> getUserid() {
        return userid;
    }

    public static void setBookid(ArrayList<String> bookid) {
        UserReqData.bookid = bookid;
    }

    public static void setContact(ArrayList<String> contact) {
        UserReqData.contact = contact;
    }

    public static void setUserid(ArrayList<String> userid) {
        UserReqData.userid = userid;
    }

    public static void setDate(ArrayList<String> date) {
        UserReqData.date = date;
    }
}
