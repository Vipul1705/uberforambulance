package com.example.cp.ambulancetracking_app;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cp.ambulancetracking_app.Adapter.HospitalListAdapter;
import com.example.cp.ambulancetracking_app.Data.Location_;
import com.example.cp.ambulancetracking_app.connect.ConnectionManager;
import com.example.cp.ambulancetracking_app.user.HospitalData;
import com.example.cp.ambulancetracking_app.user.UserSelection;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import java.util.ArrayList;

public class HospitalList extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<Status> {

    ProgressDialog progressDialog;
    int resp;
    ListView lst;
    public static ArrayList<String> id, name, cont, mail, add;
    SwipeRefreshLayout mySwipeRefreshLayout;

    private GoogleApiClient googleApiClient;
    private Location lastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        lst = (ListView) findViewById(R.id.lstHospitalview);

        createGoogleApi();

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int pos = i;
                new android.support.v7.app.AlertDialog.Builder(HospitalList.this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle(name.get(i))
                        .setMessage("Confirm?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserSelection.setSelHospitalId(id.get(pos));
                                Intent intent = new Intent(HospitalList.this, AmbulanceListActivity.class);
                                startActivity(intent);

                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });


        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("Swipe=", "onRefresh called from SwipeRefreshLayout");
                        getCellLocation();
                      /*  locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                2000,
                                10, locationListenerGPS);*/
                        Toast.makeText(HospitalList.this, "getLocation", Toast.LENGTH_LONG).show();
                    }
                }
        );
        getCellLocation();

    }


    protected void onResume() {
        super.onResume();
    }


    private void getCellLocation() {
        // CurrLocation loc = new CurrLocation();
        //loc.setCurrentLocation(this);
        if (Location_.getLatitude() > 0 || Location_.getLongitude() > 0) {
            final ConnectionManager conn = new ConnectionManager();
            if (conn.checkNetworkAvailable(HospitalList.this)) {
                progressDialog = new ProgressDialog(this);
                progressDialog.show();
                Thread tthread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            if (conn.getNHospitals()) {
                                resp = 0;
                            } else {
                                resp = 1;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        hd.sendEmptyMessage(0);

                    }
                };
                tthread.start();


            } else {
                Toast.makeText(this, "Sorry no network access.", Toast.LENGTH_LONG).show();
                if (mySwipeRefreshLayout.isRefreshing()) {
                    mySwipeRefreshLayout.setRefreshing(false);
                }
            }
        } else {
            //should be used to re-call the function?
            Toast.makeText(this, "GPS is disabled", Toast.LENGTH_LONG).show();
            if (mySwipeRefreshLayout.isRefreshing()) {
                mySwipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    public Handler hd = new Handler() {
        public void handleMessage(Message msg) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (mySwipeRefreshLayout.isRefreshing()) {
                mySwipeRefreshLayout.setRefreshing(false);
            }

            switch (resp) {
                case 0:

                    id = HospitalData.getHospid();
                    name = HospitalData.getHospname();
                    cont = HospitalData.getCont();
                    mail = HospitalData.getEmail();
                    add = HospitalData.getAddress();
                    if (id == null) {
                        Toast.makeText(HospitalList.this, "Data not found", Toast.LENGTH_LONG).show();
                    } else {
                        HospitalListAdapter adapter = new HospitalListAdapter(HospitalList.this, id, name, cont, mail, add);
                        lst.setAdapter(adapter);
                    }
                    break;
                case 1:
                    Toast.makeText(HospitalList.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };

    private void createGoogleApi() {
        //Log.d(TAG, "createGoogleApi()");
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        googleApiClient.connect();
    }

    private final int REQ_PERMISSION = 999;

    private boolean checkPermission() {
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQ_PERMISSION
        );
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Log.w(TAG, "onConnectionSuspended()");
    }

    // GoogleApiClient.OnConnectionFailedListener fail
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //Log.w(TAG, "onConnectionFailed()");
    }

    @Override
    public void onLocationChanged(Location location) {
        //Log.d(TAG, "onLocationChanged ["+location+"]");
        lastLocation = location;

        Location_.setLatitude(location.getLatitude());
        Location_.setLongitude(location.getLongitude());


        //writeActualLocation(location);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Log.i(TAG, "onConnected()");
        getLastKnownLocation();
    }

    @Override
    public void onResult(@NonNull Status status) {
        // Log.i(TAG, "onResult: " + status);
        if (status.isSuccess()) {

        } else {
            // inform about fail
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Log.d(TAG, "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    getLastKnownLocation();

                } else {
                    // Permission denied
                    Toast.makeText(HospitalList.this, "Failed !! \n Start GPS Service .....", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void getLastKnownLocation() {
        //Log.d(TAG, "getLastKnownLocation()");
        if (checkPermission()) {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastLocation != null) {
                //Log.i(TAG, "LasKnown location. " +                        "Long: " + lastLocation.getLongitude() +                        " | Lat: " + lastLocation.getLatitude());
                //writeLastLocation();
                startLocationUpdates();
            } else {
                //Log.w(TAG, "No location retrieved yet");
                startLocationUpdates();
            }
        } else askPermission();
    }

    private LocationRequest locationRequest;
    // Defined in mili seconds.
    // This number in extremely low, and should be used only for debug
    private final int UPDATE_INTERVAL = 1000;
    private final int FASTEST_INTERVAL = 900;


    private void startLocationUpdates() {
        //Log.i(TAG, "startLocationUpdates()");
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        if (checkPermission())
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }


}
