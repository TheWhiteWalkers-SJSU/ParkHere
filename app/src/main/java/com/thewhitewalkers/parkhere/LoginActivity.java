package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button buttonCreateAccount;
    private Button buttonLogin;
    private TextView textViewForgotPassword;
    private EditText editTextLoginPassword;
    private EditText editTextLoginEmail;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = new ProgressBar(this);

        if(firebaseAuth.getCurrentUser() != null) {
            // direct to homepage activity
            finish();
            startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
        }

        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword);
        editTextLoginEmail = findViewById(R.id.editTextLoginEmail);


        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //redirect to the sign up page
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //send email to reset password
                resetPassword();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String checkEmail = editTextLoginEmail.getText().toString().trim();
        String checkPassword = editTextLoginPassword.getText().toString().trim();

        //make sure user entered an email and password, otherwise do not continue
        if(TextUtils.isEmpty(checkEmail)) {
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(checkPassword)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.isShown();
        firebaseAuth.signInWithEmailAndPassword(checkEmail, checkPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        //go to home activity
                        Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
                        startActivity(intent);
                   } else {
                       //login unsuccessful, display message
                       Toast.makeText(LoginActivity.this, "Incorrect login information, please try again", Toast.LENGTH_SHORT).show();
                    }
             }
         });
    }

    private void resetPassword() {
        String email = editTextLoginEmail.getText().toString().trim();

        //make sure user entered an email
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.sendPasswordResetEmail(email);
        Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show();
    }
}
