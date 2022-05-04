package com.example.cp.ambulancetracking_app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cp.ambulancetracking_app.connect.ConnectionManager;
import com.example.cp.ambulancetracking_app.user.UserREg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserReg extends AppCompatActivity {

    EditText editName, editAddr, editEmail, editPh, editPass, editRePass;
    Button btnSub;
    ProgressDialog dg;
    int resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);

        editName = (EditText) findViewById(R.id.editName);
        editAddr = (EditText) findViewById(R.id.editAddress);
        editEmail = (EditText) findViewById(R.id.editMail);
        editPh = (EditText) findViewById(R.id.editPhone);
        editPass = (EditText) findViewById(R.id.editPass);
        editRePass = (EditText) findViewById(R.id.editRePass);

        btnSub = (Button) findViewById(R.id.btnRegSubmit);

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateSubmit();
            }
        });


    }

    public void validateSubmit() {

        final String name = editName.getText().toString().trim(),
                mail = editEmail.getText().toString().trim(),
                cont_no = editPh.getText().toString().trim(),
                address = editAddr.getText().toString().trim(),
                pass = editPass.getText().toString().trim(),
                rePass = editRePass.getText().toString().trim();

        final EditText[] allEts = {editName, editEmail, editPh, editAddr, editPass, editRePass};
        for (EditText editT : allEts) {
            if (editT.getText().toString().trim().length() == 0) {
                editT.setError("Empty Field");
                editT.requestFocus();
            }
        }

        if (!isValidPh(cont_no)) {
            editPh.setError("Invalid Phone Number");
        } else if (!isValidEmail(mail)) {
            editEmail.setError("Invalid Mail Id");
        } else if (!isValidUname(name)) {
            editName.setError("Invalid Name");
        } else if (pass.length() < 8) {
            editPass.setError("Password Length must be atleast 8 ");
        } else if (!pass.equals(rePass)) {
            editPass.setError("Password Not Matched");
        } else {//register user

            UserREg.setName(name);
            UserREg.setMail(mail);
            UserREg.setContact(cont_no);
            UserREg.setAddress(address);
            UserREg.setPass(pass);

            register();
        }

    }

    public void register() {
        final ConnectionManager conn = new ConnectionManager();
        if (ConnectionManager.checkNetworkAvailable(UserReg.this)) {
            dg = new ProgressDialog(UserReg.this);
            dg.setMessage("Processing ....");
            dg.show();

            Thread tthread = new Thread() {
                @Override
                public void run() {
                    try {
                        resp = conn.register();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    hd.sendEmptyMessage(0);

                }
            };
            tthread.start();
        } else {
            Toast.makeText(UserReg.this, "Sorry no network access.", Toast.LENGTH_LONG).show();
        }
    }

    public Handler hd = new Handler() {
        public void handleMessage(Message msg) {

            if (dg.isShowing())
                dg.dismiss();

            switch (resp) {
                case 1:
                    Toast.makeText(getApplicationContext(), "Register Successfully", Toast.LENGTH_LONG).show();
                    finish();
                    break;

                case 2:
                    Toast.makeText(getApplicationContext(), "Contact or Mail Id already exists", Toast.LENGTH_LONG).show();
                    break;

                case 3:
                    Toast.makeText(getApplicationContext(), "Try Later", Toast.LENGTH_LONG).show();
                    break;
                case 0:
                    Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    private boolean isValidPh(String ph) {
        //^[7-9][0-9]{9}$
        String EMAIL_PATTERN = "^[7-9][0-9]{9}$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(ph);
        return matcher.matches();
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidUname(String name) {
        String N_Pattern = "^([A-Za-z\\+]+[A-Za-z0-9]{1,10})$";
        Pattern pattern = Pattern.compile(N_Pattern);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }


}
