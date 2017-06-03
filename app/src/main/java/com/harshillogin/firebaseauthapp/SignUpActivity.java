package com.harshillogin.firebaseauthapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtEmail;
    private EditText password;
    private Button btnRegister;
    private TextView loginLink;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnRegister=(Button) findViewById(R.id.btnRegister);
        txtEmail=(EditText) findViewById(R.id.etEmail);
        password=(EditText) findViewById(R.id.etPassword);
        loginLink=(TextView)  findViewById(R.id.tvLoginLink);

        btnRegister.setOnClickListener(this);
        loginLink.setOnClickListener(this);

        mProgressDialog=new ProgressDialog(this);
        mFirebaseAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v==btnRegister){
            registerUser();
        }

        if(v==loginLink){
            //will open login activity
            startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            finish();
        }

    }

    private void registerUser() {
        String email=txtEmail.getText().toString().trim();
        String etPassword=password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Plz Enter Valid Email Id",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(etPassword)){
            Toast.makeText(this,"Plz Enter Valid Password",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(etPassword.length()<=5){
            Toast.makeText(this,"Password should be minimum 6 characters",Toast.LENGTH_SHORT).show();
            return;
        }
        mProgressDialog.setMessage("Registering User.....");
        mProgressDialog.show();

        mFirebaseAuth.createUserWithEmailAndPassword(email,etPassword)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mProgressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this,"User registered Successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mProgressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this,"User not registered!!Try Again",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
