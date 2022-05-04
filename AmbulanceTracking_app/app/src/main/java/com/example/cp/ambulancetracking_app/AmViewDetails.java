package com.example.cp.ambulancetracking_app;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cp.ambulancetracking_app.connect.ConnectionManager;

public class AmViewDetails extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.cp.ambulancetracking_app";
    ProgressDialog progressDialog;
    int resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_view_details);

        Button btndone = (Button) findViewById(R.id.btnDone);
        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new android.support.v7.app.AlertDialog.Builder(AmViewDetails.this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("Done")
                        .setMessage("Are you sure ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                closeRequest();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        Button btnUser = (Button) findViewById(R.id.btnViewUserLoc);
        Button btnHosp = (Button) findViewById(R.id.btnViewHospLoc);
        Button btnLogout = findViewById(R.id.btnLogout);

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOnMap("user");
            }
        });

        btnHosp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOnMap("hosp");
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AmViewDetails.this, AmLogin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });


    }

    public void viewOnMap(String msg)
    {
        Intent intent = new Intent(this, Map.class);
        intent.putExtra(EXTRA_MESSAGE, msg);
        startActivity(intent);
    }

    public void closeRequest() {
        final ConnectionManager conn = new ConnectionManager();
        if (conn.checkNetworkAvailable(AmViewDetails.this)) {
            progressDialog = new ProgressDialog(AmViewDetails.this);
            progressDialog.show();
            Thread th1 = new Thread() {
                @Override
                public void run() {
                    try {
                        if (conn.closeRequest()) {
                            resp = 0;
                        } else {
                            resp = 1;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    closeHd.sendEmptyMessage(0);

                }
            };
            th1.start();
        } else {
            Toast.makeText(AmViewDetails.this, "Sorry no network access.", Toast.LENGTH_LONG).show();
        }
    }

    public Handler closeHd = new Handler() {
        public void handleMessage(Message msg) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            switch (resp) {
                case 0:
                    Intent intent = new Intent(AmViewDetails.this, AmbuDonePay.class);
                    startActivity(intent);
                    finish();
                    break;

                case 1:

                    Toast.makeText(AmViewDetails.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };


}
