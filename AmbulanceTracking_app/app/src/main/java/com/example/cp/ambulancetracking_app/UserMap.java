package com.example.cp.ambulancetracking_app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.cp.ambulancetracking_app.user.BookedAmbuDetails;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UserMap extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap googlemap;
    String name;
    String latitude, longitude, cn;
    TextView tvAmbName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_map);

        tvAmbName = (TextView) findViewById(R.id.tvAmbName);

        tvAmbName.setText(BookedAmbuDetails.getName());


        latitude = BookedAmbuDetails.getLat();
        longitude = BookedAmbuDetails.getLon();
        name = BookedAmbuDetails.getName()+"\n"+BookedAmbuDetails.getCn()+"\n"+BookedAmbuDetails.getVehicleNo();

        final ActivityManager activitymanager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activitymanager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
        if (supportsEs2) {
                /*if(latitude.equals(null)||longitude.equals(null))
                {
        			latitude="0.0";
        			longitude="0.0";
        		}*/
            if (googlemap == null) {
                MapFragment mFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.umap);
                mFrag.getMapAsync(this);
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap map) {
        //DO WHATEVER YOU WANT WITH GOOGLEMAP

        googlemap = map;

        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude))).title(name);
        CameraPosition camera = new CameraPosition.Builder().target(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude))).zoom(15).build();
        if (map != null) {
            //plot point
            googlemap.addMarker(marker);
            //focus camera to point
            googlemap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
        }
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.setMyLocationEnabled(true);
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }
}
