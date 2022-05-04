package com.example.cp.ambulancetracking_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SelectLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login);

        Button btnUser = (Button) findViewById(R.id.btnUL);
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SelectLoginActivity.this,UserLoginActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        Button btnAmb = (Button) findViewById(R.id.btnAML);
        btnAmb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SelectLoginActivity.this,AmLogin.class);
                startActivity(intent);
                //finish();
            }
        });

    }
}
