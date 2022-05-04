package com.example.cp.ambulancetracking_app.user;

public class UserSelection {

    public static String selHospitalId;
    public static String selAmbulanceId;
    public static String desc;

    public static String getSelAmbulanceId() {
        return selAmbulanceId;
    }

    public static String getSelHospitalId() {
        return selHospitalId;
    }

    public static void setSelAmbulanceId(String selAmbulanceId) {
        UserSelection.selAmbulanceId = selAmbulanceId;
    }

    public static void setSelHospitalId(String selHospitalId) {
        UserSelection.selHospitalId = selHospitalId;
    }

    public static String getDesc() {
        return desc;
    }

    public static void setDesc(String desc) {
        UserSelection.desc = desc;
    }
}
