package com.harshillogin.firebaseauthapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvEmail;
    private Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tvEmail=(TextView) findViewById(R.id.wEmailId);
        tvEmail.setText(getIntent().getStringExtra("EmailId"));

        btnLogout=(Button) findViewById(R.id.wLogout);
        btnLogout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==btnLogout){
            Toast.makeText(WelcomeActivity.this,"User Log Out Successfully",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
        }
    }
}
