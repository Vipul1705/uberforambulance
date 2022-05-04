package com.example.cp.ambulancetracking_app.user;


import java.util.ArrayList;

public class AmbulanceData {

    public static ArrayList<String> ambuId;
    public static ArrayList<String> ambuName,ambulat,ambulon,ambuContact,VehicleNo;

    public static ArrayList<String> getAmbuContact() {
        return ambuContact;
    }

    public static ArrayList<String> getAmbuId() {
        return ambuId;
    }

    public static ArrayList<String> getAmbuName() {
        return ambuName;
    }

    public static ArrayList<String> getAmbulat() {
        return ambulat;
    }

    public static void setAmbuId(ArrayList<String> ambuId) {
        AmbulanceData.ambuId = ambuId;
    }

    public static ArrayList<String> getAmbulon() {
        return ambulon;
    }

    public static void setAmbulat(ArrayList<String> ambulat) {
        AmbulanceData.ambulat = ambulat;
    }

    public static void setAmbulon(ArrayList<String> ambulon) {
        AmbulanceData.ambulon = ambulon;
    }

    public static void setAmbuContact(ArrayList<String> ambuContact) {
        AmbulanceData.ambuContact = ambuContact;
    }

    public static void setAmbuName(ArrayList<String> ambuName) {
        AmbulanceData.ambuName = ambuName;
    }

    public static ArrayList<String> getVehicleNo() {
        return VehicleNo;
    }

    public static void setVehicleNo(ArrayList<String> vehicleNo) {
        VehicleNo = vehicleNo;
    }
}
