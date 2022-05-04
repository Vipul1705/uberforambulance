package com.example.cp.ambulancetracking_app.ambulance;

public class LogAmbDriver {

    public static String ambulance_id;
    public static String driver_name;

    public static String getAmbulance_id() {
        return ambulance_id;
    }

    public static String getDriver_name() {
        return driver_name;
    }

    public static void setAmbulance_id(String ambulance_id) {
        LogAmbDriver.ambulance_id = ambulance_id;
    }

    public static void setDriver_name(String driver_name) {
        LogAmbDriver.driver_name = driver_name;
    }

}
