package com.example.cp.ambulancetracking_app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cp.ambulancetracking_app.ambulance.AccpetedReq;
import com.example.cp.ambulancetracking_app.connect.ConnectionManager;

public class AmbuDonePay extends AppCompatActivity {

    TextView txtUname, txtHospname;
    EditText editCharge;
    Button btnSubmit;

    ProgressDialog progressDialog;
    int resp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambu_done_pay);

        txtUname = (TextView) findViewById(R.id.txtUPayname);
        txtHospname = (TextView) findViewById(R.id.txtHPayname);
        editCharge = (EditText) findViewById(R.id.editCharges);
        btnSubmit = (Button) findViewById(R.id.btnChargesSubmit);

        txtUname.setText(AccpetedReq.getUname());
        txtHospname.setText(AccpetedReq.getHospname());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateChargesSubmit();

            }
        });

    }


    public void validateChargesSubmit() {
        final ConnectionManager conn = new ConnectionManager();
        if (conn.checkNetworkAvailable(AmbuDonePay.this)) {
            progressDialog = new ProgressDialog(AmbuDonePay.this);
            progressDialog.show();
            Thread th1 = new Thread() {
                @Override
                public void run() {
                    try {
                        int i = conn.PayCharges(editCharge.getText().toString().trim());
                        resp = i;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    hd.sendEmptyMessage(0);

                }
            };
            th1.start();
        } else {
            Toast.makeText(AmbuDonePay.this, "Sorry no network access.", Toast.LENGTH_LONG).show();
        }

    }

    public Handler hd = new Handler() {
        public void handleMessage(Message msg) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            switch (resp) {
                case 1:
                    finish();
                    Toast.makeText(getApplicationContext(), "Record Saved", Toast.LENGTH_LONG).show();
                    break;

                case 2:
                    Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_LONG).show();
                    break;
                case 0:
                    Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

}
