package com.example.cp.ambulancetracking_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cp.ambulancetracking_app.connect.ConnectionManager;

public class AmLogin extends AppCompatActivity {

    ProgressDialog progressDialog;
    int resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_login);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    public void login() {
        final EditText editMail = (EditText) findViewById(R.id.editUsername);
        final EditText editPass = (EditText) findViewById(R.id.editPassword);
        if (editMail.getText().toString().equals("") || editPass.getText().toString().equals("")) {
            android.app.AlertDialog alert = new android.app.AlertDialog.Builder(AmLogin.this).create();
            alert.setTitle("Enter All Details");
            alert.setMessage("All Fields Are Mandatory");
            alert.show();
        } else {
            final String uname = editMail.getText().toString().trim();
            final String pass = editPass.getText().toString().trim();
            final ConnectionManager conn = new ConnectionManager();
            if (conn.checkNetworkAvailable(AmLogin.this)) {
                progressDialog = new ProgressDialog(AmLogin.this);

                progressDialog.show();
                Thread th1 = new Thread() {
                    @Override
                    public void run() {
                        try {
                            int i = conn.authAmbulance(uname, pass);
                            resp = i;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        hd.sendEmptyMessage(0);

                    }
                };
                th1.start();
            } else {
                Toast.makeText(AmLogin.this, "Sorry no network access.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public Handler hd = new Handler() {
        public void handleMessage(Message msg) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            switch (resp) {
                case 1:
                    final EditText editPass = (EditText) findViewById(R.id.editPassword);
                    editPass.setText("");
                    Intent intent = new Intent(AmLogin.this, AmHomeActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                    break;

                case 2:
                    final EditText editPass2 = (EditText) findViewById(R.id.editPassword);
                    editPass2.setText("");
                    Toast.makeText(getApplicationContext(), "Invalid Mail or Password", Toast.LENGTH_LONG).show();
                    break;
                case 0:
                    Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

}
