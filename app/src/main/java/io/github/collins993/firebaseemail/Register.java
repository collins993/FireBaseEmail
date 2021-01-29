package io.github.collins993.firebaseemail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Register extends AppCompatActivity {

    EditText fullName, emailAddress, password, confirmPassword;
    Button registerUser, loginButton;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Initialize Varables

        firebaseAuth = FirebaseAuth.getInstance();

        fullName = findViewById(R.id.sign_up_full_name);

        emailAddress = findViewById(R.id.sign_up_email);

        password = findViewById(R.id.sign_up_password);

        confirmPassword = findViewById(R.id.sign_up_confirm_password);

        registerUser = findViewById(R.id.sign_up_next_btn);

        loginButton = findViewById(R.id.sign_up_login_btn);

        //validate data
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Fullname = fullName.getText().toString();
                String Email = emailAddress.getText().toString();
                String Password = password.getText().toString();
                String ConfirmPassword = confirmPassword.getText().toString();

                if (Fullname.isEmpty()) {

                    fullName.setError("Fullname is required");
                    return;
                }

                if (Email.isEmpty()) {

                    emailAddress.setError("Email is required");
                    return;
                }

                if (Password.isEmpty()) {

                    password.setError("Password is required");
                    return;
                }

                if (ConfirmPassword.isEmpty()) {

                    confirmPassword.setError("Confirmation Password is required");
                    return;
                }

                if (!Password.equals(ConfirmPassword)) {

                    confirmPassword.setError("Password do not match");
                    return;
                }

                Toast.makeText(Register.this, "Data Validated", Toast.LENGTH_SHORT).show();

                firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                Toast.makeText(Register.this, "Registered Successful", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(Register.this, MainActivity.class));

                                finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }


        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Register.this, Login.class));

                finish();
            }
        });


    }


    //VALIDATION CODE

    /**

    private boolean validateFullName() {

        String val = Objects.requireNonNull(fullName.getEditText()).getText().toString().trim();

        if (val.isEmpty()) {

            fullName.setError("Field can not be empty");

            return false;
        } else {

            fullName.setError(null);

            fullName.setErrorEnabled(false);

            return true;
        }
    }

    private boolean validateUserName() {

        String val = Objects.requireNonNull(userName.getEditText()).getText().toString().trim();

//                String checkSpaces = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {

            userName.setError("Field can not be empty");
            return false;

        } else if (val.length() > 20) {

            userName.setError("Username can not be more than 20.");
            return false;

        } else {

            userName.setError(null);

            userName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {

        String val = emailAddress.getEditText().getText().toString().trim();

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
            emailAddress.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {

        String val = password.getEditText().getText().toString().trim();

        String val2 = confirmPassword.getEditText().getText().toString().trim();

//                String checkPassword = "^" +
//                        "(?=.*[0-9])" +             //at least 1 digit
//                        "(?=.*[a-z])" +             //at least 1 lower case letter
//                        "(?=.*[A-Z])" +             //at least 1 upper case letter
//                        "(?=.*[a-zA-Z])" +          //any letter
//                        "(?=.*[@#$%^&+=])" +        //at least 1 special character
//                        "(?=\\S+$)" +               //no white spaces
//                        ".{4,}" +                   //at least 4 characters
//                        "$";

        if (val.isEmpty() && !val.matches(val2)) {

            password.setError("Field can not be empty");
            return false;
        }
        else {

            password.setError(null);

            password.setErrorEnabled(false);
            return true;
        }
    }
**/


}