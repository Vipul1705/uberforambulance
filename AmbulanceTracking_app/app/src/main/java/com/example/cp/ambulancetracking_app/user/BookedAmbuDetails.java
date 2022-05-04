package com.example.cp.ambulancetracking_app.user;


public class BookedAmbuDetails {

    public static String bookid,ambiId,cn,lat,lon,name,VehicleNo;

    public static String getAmbiId() {
        return ambiId;
    }

    public static String getBookid() {
        return bookid;
    }

    public static String getCn() {
        return cn;
    }

    public static String getLat() {
        return lat;
    }

    public static void setAmbiId(String ambiId) {
        BookedAmbuDetails.ambiId = ambiId;
    }

    public static String getLon() {
        return lon;
    }

    public static void setBookid(String bookid) {
        BookedAmbuDetails.bookid = bookid;
    }

    public static void setCn(String cn) {
        BookedAmbuDetails.cn = cn;
    }

    public static String getName() {
        return name;
    }

    public static void setLat(String lat) {
        BookedAmbuDetails.lat = lat;
    }

    public static void setLon(String lon) {
        BookedAmbuDetails.lon = lon;
    }

    public static void setName(String name) {
        BookedAmbuDetails.name = name;
    }

    public static String getVehicleNo() {
        return VehicleNo;
    }

    public static void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
    }
}
