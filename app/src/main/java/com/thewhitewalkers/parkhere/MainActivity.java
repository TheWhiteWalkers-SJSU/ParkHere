package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextLocation;
    private EditText editTextPhoneNumber;
    private TextView textViewSignIn;

    private FirebaseAuth firebaseAuth;
    DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null) {
            // direct to homepage activity if user is already logged in
            finish();
            startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
        }

        buttonRegister = findViewById(R.id.buttonRegister);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

        textViewSignIn = findViewById(R.id.textViewSignin);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerUser();
            }
        });

        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // send the user to the log in page
                finish();
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String firstName = editTextFirstName.getText().toString().trim();
        final String lastName = editTextLastName.getText().toString().trim();
        final String location = editTextLocation.getText().toString().trim();
        final String phoneNumber = editTextPhoneNumber.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            // email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            // stopping the function execution
            return;
        }

        if(TextUtils.isEmpty(password)) {
            // password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            // stopping the function execution
            return;
        }

        try{
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {

                                //make User and save to the firebase db
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                String _id = user.getUid();
                                User newUser = new User(_id, email, firstName, lastName, location, phoneNumber);
                                userDatabase.child(_id).setValue(newUser);
                                Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                if(firebaseAuth.getCurrentUser() != null) {
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Could not register successfully..please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (DatabaseException e){
            Toast.makeText(MainActivity.this, "Could not register successfully..please try again", Toast.LENGTH_SHORT).show();
        }

    }
}
