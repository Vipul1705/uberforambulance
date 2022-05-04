package com.example.cp.ambulancetracking_app.Data;


import android.content.Context;
import android.location.LocationListener;

import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;


public class CurrLocation {


    public void setCurrentLocation(Context context) {
        try {
            //Call method to search using location on main thread
            if (Looper.myLooper() == null)
            {
                Looper.prepare();
            }


            // Define a listener that responds to location updates
            LocationListener locationListener = new LocationListener() {

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }

                public void onStatusChanged(String provider, int status,
                                            Bundle extras) {
                    // TODO Auto-generated method stub

                }

                public void onLocationChanged(android.location.Location location) {
                    // TODO Auto-generated method stub
                    double lat = location.getLatitude();
                    double lng = location.getLongitude();
                    float accuracy = location.getAccuracy();
                    //return values
                    Location_.setLatitude(lat);
                    Location_.setLongitude(lng);
                }
            };

            // Acquire a reference to the system Location Manager
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            //Check if location manager is enabled on the phone
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                // Register the listener with the Location Manager to receive location updates
                try {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                } catch (SecurityException e) {

                }
            } else {
                // Register the listener with the Location Manager to receive location updates
                try {
                    locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, locationListener);
                } catch (SecurityException e) {

                }
            }
           // Looper.loop();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            System.out.println(msg);
        }
    }

}
