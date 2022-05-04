package com.example.cp.ambulancetracking_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cp.ambulancetracking_app.connect.ConnectionManager;

public class UserHome extends AppCompatActivity {

    public static Intent UpdateLocation;
    ProgressDialog progressDialog;
    int resp;
    Button btnLogout,btnViewCharges;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        Button btnTrack = (Button) findViewById(R.id.btnTrackAmbu);
        btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAmbuDetails();
            }
        });

        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHome.this, UserLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        btnViewCharges=(Button)findViewById(R.id.btnViewBills);
        btnViewCharges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserHome.this,ViewChargeList.class);
                startActivity(intent);
            }
        });

    }

    public void getAmbuDetails() {
        final ConnectionManager conn = new ConnectionManager();
        if (conn.checkNetworkAvailable(UserHome.this)) {
            progressDialog = new ProgressDialog(this);
            progressDialog.show();
            Thread tthread = new Thread() {
                @Override
                public void run() {
                    try {
                        int i = conn.getAmbulanceDetails();
                        resp = i;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ambhd.sendEmptyMessage(0);

                }
            };
            tthread.start();


        } else {
            Toast.makeText(this, "Sorry no network access.", Toast.LENGTH_LONG).show();
        }
    }

    public Handler ambhd = new Handler() {
        public void handleMessage(Message msg) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            switch (resp) {
                case 0:
                    Toast.makeText(UserHome.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Intent intent = new Intent(UserHome.this, UserMap.class);
                    startActivity(intent);
                    break;

                case 2:
                    Toast.makeText(UserHome.this, "Booking Request bot accepted yet", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };


}
