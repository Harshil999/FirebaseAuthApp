package com.harshillogin.firebaseauthapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText txtEmail;
    private Button btnResetPassword, btnBack;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        btnResetPassword=(Button) findViewById(R.id.btnResetPassword);
        txtEmail=(EditText) findViewById(R.id.fEmailId);
        btnBack=(Button) findViewById(R.id.fBtnBack);

        btnResetPassword.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        mProgressDialog=new ProgressDialog(this);
        mFirebaseAuth=FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        if(v==btnBack){
            finish();
        }

        if(v==btnResetPassword){
            resetPassword();
        }
    }

    private void resetPassword() {
        String email = txtEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Plz Enter Valid Email Id", Toast.LENGTH_SHORT).show();
            return;
        }

        mProgressDialog.setMessage("Checking In  Progress.....");
        mProgressDialog.show();

        mFirebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mProgressDialog.dismiss();
                            Toast.makeText(ResetPasswordActivity.this, "Password Reset Successfully", Toast.LENGTH_SHORT).show();
                            if (mFirebaseAuth.getCurrentUser() != null) {
                                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                                finish();
                            }
                        }else {
                                mProgressDialog.dismiss();
                                Toast.makeText(ResetPasswordActivity.this, "Failed to send reset Email instructions!!Try Again", Toast.LENGTH_SHORT).show();
                            }
                    }
                });

    }
}
