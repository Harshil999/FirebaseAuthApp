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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText txtEmail;
    private EditText password;
    private Button btnLogin;
    private TextView signupLink;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth mFirebaseAuth;
    private Button btnForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        btnLogin=(Button) findViewById(R.id.btnLogin);
        txtEmail=(EditText) findViewById(R.id.etLoginEmail);
        password=(EditText) findViewById(R.id.etLoginPassword);
        signupLink=(TextView)  findViewById(R.id.tvSignUpLink);
        btnForgotPassword=(Button) findViewById(R.id.btnForgotPassword);

        btnLogin.setOnClickListener(this);
        signupLink.setOnClickListener(this);
        btnForgotPassword.setOnClickListener(this);

        mProgressDialog=new ProgressDialog(this);
        mFirebaseAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v==btnLogin){
            validateUser();
        }

        if(v==signupLink){
            //will open login activity
            startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            finish();
        }

        if(v==btnForgotPassword){
            startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
        }
    }

    private void validateUser() {
        final String email = txtEmail.getText().toString().trim();
        String etPassword = password.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Plz Enter Valid Email Id", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(etPassword)) {
            Toast.makeText(this, "Plz Enter Valid Password", Toast.LENGTH_SHORT).show();
            return;
        } else if (etPassword.length() <= 5) {
            Toast.makeText(this, "Password should be minimum 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        mProgressDialog.setMessage("Checking In  Progress.....");
        mProgressDialog.show();

        mFirebaseAuth.signInWithEmailAndPassword(email, etPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mProgressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "User Login Successfully", Toast.LENGTH_SHORT).show();
                            if (mFirebaseAuth.getCurrentUser() != null) {
                                startActivity(new Intent(LoginActivity.this, WelcomeActivity.class).putExtra("EmailId",email));
                                finish();
                            }
                        }else {
                            mProgressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "User Login not Successful!!Try Again", Toast.LENGTH_SHORT).show();
                        }

                }
        });
    }
}
