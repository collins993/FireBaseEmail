package io.github.collins993.firebaseemail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPassword extends AppCompatActivity {

    EditText newPassword, confirmNewPassword;
    Button savePassButton;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        newPassword = findViewById(R.id.new_user_password);

        confirmNewPassword = findViewById(R.id.confirm_new_password);

        savePassButton = findViewById(R.id.reset_password_btn);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        savePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (newPassword.getText().toString().isEmpty()) {

                    newPassword.setError("Required Field");
                    return;
                }

                if (confirmNewPassword.getText().toString().isEmpty()) {

                    confirmNewPassword.setError("Required Field");
                    return;
                }

                if (!newPassword.getText().toString().equals(confirmNewPassword.getText().toString())) {

                    confirmNewPassword.setError("Password do not Match");
                    return;
                }

                firebaseUser.updatePassword(newPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(ResetPassword.this, "Password Updated", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(ResetPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}