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
import android.widget.ListView;
import android.widget.Toast;

import com.example.cp.ambulancetracking_app.Adapter.UserReqAdapter;
import com.example.cp.ambulancetracking_app.ambulance.AccpetedReq;
import com.example.cp.ambulancetracking_app.ambulance.UserReqData;
import com.example.cp.ambulancetracking_app.connect.ConnectionManager;

import java.util.ArrayList;

public class AmHomeActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    int resp;
    ListView lst;
    public static Intent UpdateLocation;
    SwipeRefreshLayout mySwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_home);

        mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        UpdateLocation = new Intent(getApplicationContext(), LocationService.class);
        Toast.makeText(getApplicationContext(), "Location Update Service Is Started", Toast.LENGTH_SHORT).show();
        startService(UpdateLocation);

        getRequests();

        lst = (ListView) findViewById(R.id.lstUserReq);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                new android.support.v7.app.AlertDialog.Builder(AmHomeActivity.this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle(UserReqData.getContact().get(i).toString())
                        .setMessage("Are you sure you want confirm?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AccpetedReq.setBookid(UserReqData.getBookid().get(i));
                                AccpetedReq.setUserid(UserReqData.getUserid().get(i));
                                acceptRequest(UserReqData.getBookid().get(i));
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
                        getRequests();
                    }
                }
        );

    }

    public void acceptRequest(final String bid) {
        final ConnectionManager conn = new ConnectionManager();
        if (conn.checkNetworkAvailable(AmHomeActivity.this)) {
            progressDialog = new ProgressDialog(AmHomeActivity.this);
            progressDialog.show();
            Thread th1 = new Thread() {
                @Override
                public void run() {
                    try {
                        int i = conn.acceptRequest(bid);
                        resp = i;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    acceptHd.sendEmptyMessage(0);

                }
            };
            th1.start();
        } else {
            Toast.makeText(AmHomeActivity.this, "Sorry no network access.", Toast.LENGTH_LONG).show();
        }
    }

    public Handler acceptHd = new Handler() {
        public void handleMessage(Message msg) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            switch (resp) {
                case 0:

                    Toast.makeText(AmHomeActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    break;

                case 1:

                    Intent intent = new Intent(AmHomeActivity.this, AmViewDetails.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Accepted", Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case 2:
                    Toast.makeText(AmHomeActivity.this, "Invalid", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void getRequests() {
        final ConnectionManager conn = new ConnectionManager();
        if (conn.checkNetworkAvailable(AmHomeActivity.this)) {
            progressDialog = new ProgressDialog(AmHomeActivity.this);
            progressDialog.show();
            Thread th1 = new Thread() {
                @Override
                public void run() {
                    try {
                        if (conn.viewRequests()) {
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
            th1.start();
        } else {
            Toast.makeText(AmHomeActivity.this, "Sorry no network access.", Toast.LENGTH_LONG).show();
            if (mySwipeRefreshLayout.isRefreshing()) {
                mySwipeRefreshLayout.setRefreshing(false);
            }
        }

    }

    public Handler hd = new Handler() {
        public void handleMessage(Message msg) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            if (mySwipeRefreshLayout.isRefreshing()) {
                mySwipeRefreshLayout.setRefreshing(false);
            }

            switch (resp) {
                case 0:

                    ArrayList<String> bid, uid, cn, dt;
                    bid = UserReqData.getBookid();
                    uid = UserReqData.getUserid();
                    cn = UserReqData.getContact();
                    dt = UserReqData.getDate();
                    if (!bid.isEmpty()) {
                        UserReqAdapter adapter = new UserReqAdapter(AmHomeActivity.this, bid, uid, cn, dt);

                        lst.setAdapter(adapter);
                    } else {
                        Toast.makeText(AmHomeActivity.this, "No record found", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case 1:

                    Toast.makeText(getApplicationContext(), "Data not Found", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

}
