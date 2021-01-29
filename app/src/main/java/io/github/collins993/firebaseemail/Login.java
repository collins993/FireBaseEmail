package io.github.collins993.firebaseemail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private Button createAccount, logIn, forgotPasswordBtn;
    private EditText emailAddress, password;
    FirebaseAuth firebaseAuth;
    AlertDialog.Builder resetAlert;
    LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        firebaseAuth = FirebaseAuth.getInstance();

        resetAlert = new AlertDialog.Builder(this);

        inflater = this.getLayoutInflater();

        createAccount = findViewById(R.id.btn_create_acc);

        logIn = findViewById(R.id.btn_login);

        forgotPasswordBtn = findViewById(R.id.login_forget_password);

        emailAddress = findViewById(R.id.edt_email);

        password = findViewById(R.id.edt_password);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Login.this, Register.class));


            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (emailAddress.getText().toString().isEmpty()) {

                    emailAddress.setError("Email is Invalid");
                    return;
                }

                if (password.getText().toString().isEmpty()) {

                    password.setError("Password is Invalid");
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(emailAddress.getText().toString(), password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                startActivity(new Intent(Login.this, MainActivity.class));

                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View v  = inflater.inflate(R.layout.reset_pop, null);

                resetAlert.setTitle("Reset Forgot Password")
                        .setMessage("Enter Email to get Password Reset Link")
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                EditText email = v.findViewById(R.id.reset_email_pop);

                                if (email.getText().toString().isEmpty()) {

                                    email.setError("Required Field");
                                    return;
                                }

                                firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(Login.this, "Reset Email Sent", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }).setNegativeButton("Cancel", null)
                        .setView(v)
                        .create().show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //to check if user is currently logged in
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            startActivity(new Intent(Login.this, MainActivity.class));

            finish();
        }
    }

    /**
    private boolean validateEmail() {

        String val = emailAddress.getText().toString().trim();

        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {

            emailAddress.setError("Field can not be empty");
            return false;
        }
        else if (!val.matches(checkEmail)) {

            emailAddress.setError("Invalid Email");
            return false;
        }
        else {

            emailAddress.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {

        String val = password.getText().toString().trim();


//                String checkPassword = "^" +
//                        "(?=.*[0-9])" +             //at least 1 digit
//                        "(?=.*[a-z])" +             //at least 1 lower case letter
//                        "(?=.*[A-Z])" +             //at least 1 upper case letter
//                        "(?=.*[a-zA-Z])" +          //any letter
//                        "(?=.*[@#$%^&+=])" +        //at least 1 special character
//                        "(?=\\S+$)" +               //no white spaces
//                        ".{4,}" +                   //at least 4 characters
//                        "$";

        if (val.isEmpty()) {

            password.setError("Field can not be empty");
            return false;
        }
        else {

            password.setError(null);

            return true;
        }
    }
     **/
}