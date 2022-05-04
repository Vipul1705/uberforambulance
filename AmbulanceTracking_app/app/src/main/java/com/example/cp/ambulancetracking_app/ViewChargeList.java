package com.example.cp.ambulancetracking_app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cp.ambulancetracking_app.Adapter.ChargeAdapter;
import com.example.cp.ambulancetracking_app.connect.ConnectionManager;
import com.example.cp.ambulancetracking_app.user.ChargeListData;

import java.util.ArrayList;

public class ViewChargeList extends AppCompatActivity {

    ListView lst;
    ProgressDialog progressDialog;
    int resp;

    public static ArrayList<String> chrgeid, name, charges, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_charge_list);

        lst = (ListView) findViewById(R.id.lstViewCharges);

        getChargesList();


    }


    public void getChargesList() {
        final ConnectionManager conn = new ConnectionManager();
        if (conn.checkNetworkAvailable(ViewChargeList.this)) {
            progressDialog = new ProgressDialog(this);
            progressDialog.show();
            Thread tthread = new Thread() {
                @Override
                public void run() {
                    try {
                        if (conn.getChargesData()) {
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
        }
    }

    public Handler hd = new Handler() {
        public void handleMessage(Message msg) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            switch (resp) {
                case 0:
                    chrgeid = ChargeListData.getChrgeid();
                    name = ChargeListData.getAmbname();
                    charges = ChargeListData.getCharges();
                    date = ChargeListData.getDate1();

                    if (chrgeid == null) {
                        Toast.makeText(ViewChargeList.this, "Data not found", Toast.LENGTH_LONG).show();
                    } else {
                        ChargeAdapter adapter = new ChargeAdapter(ViewChargeList.this, chrgeid, name, charges, date);
                        lst.setAdapter(adapter);
                    }
                    break;
                case 1:
                    Toast.makeText(ViewChargeList.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };


}
