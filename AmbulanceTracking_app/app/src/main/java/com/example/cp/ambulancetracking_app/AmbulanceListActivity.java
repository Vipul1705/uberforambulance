package com.example.cp.ambulancetracking_app;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cp.ambulancetracking_app.Adapter.AmbuListadapter;
import com.example.cp.ambulancetracking_app.connect.ConnectionManager;
import com.example.cp.ambulancetracking_app.user.AmbulanceData;
import com.example.cp.ambulancetracking_app.user.UserSelection;

import java.util.ArrayList;

public class AmbulanceListActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    int resp;
    ListView lstAmbuList;
    public static ArrayList<String> id, name, cont,vehicleNo;
    SwipeRefreshLayout mySwipeRefreshLayout;
    EditText et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_list);
        et = new EditText(this);
        et.setHint("Enter description for patient");
        lstAmbuList = (ListView) findViewById(R.id.lstAMview);
        mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeAmbList);
        getNAmbulaceList();


        lstAmbuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int pos = i;
                new android.support.v7.app.AlertDialog.Builder(AmbulanceListActivity.this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle(name.get(i))
                        .setMessage("Confirm?")
                        .setView(et)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               // Toast.makeText(getApplicationContext(),et.getText().toString(),Toast.LENGTH_LONG).show();
                                UserSelection.setSelAmbulanceId(id.get(pos));
                                UserSelection.setDesc(et.getText().toString());
                                bookAmbulance();
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
                        getNAmbulaceList();
                    }
                }
        );

    }

    public void getNAmbulaceList() {
        final ConnectionManager conn = new ConnectionManager();
        if (conn.checkNetworkAvailable(AmbulanceListActivity.this)) {
            progressDialog = new ProgressDialog(this);
            progressDialog.show();
            Thread tthread = new Thread() {
                @Override
                public void run() {
                    try {
                        if (conn.getNAmbulance()) {
                            resp = 0;
                        } else {
                            resp = 1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ambuhd.sendEmptyMessage(0);

                }
            };
            tthread.start();


        } else {
            Toast.makeText(this, "Sorry no network access.", Toast.LENGTH_LONG).show();
            if(mySwipeRefreshLayout.isRefreshing())
            {
                mySwipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    public Handler ambuhd = new Handler() {
        public void handleMessage(Message msg) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (mySwipeRefreshLayout.isRefreshing()) {
                mySwipeRefreshLayout.setRefreshing(false);
            }

            switch (resp) {
                case 0:

                    id = AmbulanceData.getAmbuId();
                    name = AmbulanceData.getAmbuName();
                    cont = AmbulanceData.getAmbuContact();
                    vehicleNo = AmbulanceData.getVehicleNo();

                    if (id.isEmpty()) {
                        Toast.makeText(AmbulanceListActivity.this, "Data not found", Toast.LENGTH_LONG).show();
                    } else {
                        AmbuListadapter adapter = new AmbuListadapter(AmbulanceListActivity.this, id, name, cont,vehicleNo);
                        lstAmbuList.setAdapter(adapter);
                    }
                    break;
                case 1:
                    Toast.makeText(AmbulanceListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };

    public void bookAmbulance() {
        final ConnectionManager conn = new ConnectionManager();
        if (conn.checkNetworkAvailable(AmbulanceListActivity.this)) {
            progressDialog = new ProgressDialog(this);
            progressDialog.show();
            Thread tthread = new Thread() {
                @Override
                public void run() {
                    try {
                        if (conn.bookAmbulance()) {
                            resp = 0;
                        } else {
                            resp = 1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    bookHd.sendEmptyMessage(0);

                }
            };
            tthread.start();


        } else {
            Toast.makeText(this, "Sorry no network access.", Toast.LENGTH_LONG).show();
        }
    }

    public Handler bookHd = new Handler() {
        public void handleMessage(Message msg) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            switch (resp) {
                case 0:
                    Intent intent = new Intent(AmbulanceListActivity.this, UserHome.class);
                    startActivity(intent);
                    break;
                case 1:
                    Toast.makeText(AmbulanceListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };

}
