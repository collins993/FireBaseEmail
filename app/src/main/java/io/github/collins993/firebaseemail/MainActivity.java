package io.github.collins993.firebaseemail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button logOut, verifyEmail;
    TextView verifyMsg;
    ImageView moreOptions;
    FirebaseAuth firebaseAuth;
    AlertDialog.Builder resetAlert;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        firebaseAuth = FirebaseAuth.getInstance();

        resetAlert = new AlertDialog.Builder(this);

        inflater = this.getLayoutInflater();

        logOut = findViewById(R.id.log_out);

        verifyEmail = findViewById(R.id.btn_verify);

        verifyMsg = findViewById(R.id.textView2);
        
        moreOptions = findViewById(R.id.more_options);

        if (!firebaseAuth.getCurrentUser().isEmailVerified()) {

            verifyEmail.setVisibility(View.VISIBLE);
            verifyMsg.setVisibility(View.VISIBLE);
        }

        verifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(MainActivity.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();

                        verifyEmail.setVisibility(View.GONE);
                        verifyMsg.setVisibility(View.GONE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(getApplicationContext(), Login.class));

                finish();
            }
        });
        
        moreOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);

                popupMenu.getMenuInflater().inflate(R.menu.options_menu, popupMenu.getMenu());

                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.reset_password:

                                Toast.makeText(MainActivity.this, "Reset Password Clicked", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(), ResetPassword.class));
                                break;

                            case R.id.update_email:

                                Toast.makeText(MainActivity.this, "Update Email Clicked", Toast.LENGTH_SHORT).show();

                                View v  = inflater.inflate(R.layout.reset_pop, null);

                                resetAlert.setTitle("Update Email")
                                        .setMessage("Enter New Email Address")
                                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                EditText email = v.findViewById(R.id.reset_email_pop);

                                                if (email.getText().toString().isEmpty()) {

                                                    email.setError("Required Field");
                                                    return;
                                                }

                                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                                                firebaseUser.updateEmail(email.getText().toString())
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                        Toast.makeText(MainActivity.this, "Email Updated", Toast.LENGTH_SHORT).show();

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            }
                                        }).setNegativeButton("Cancel", null)
                                        .setView(v)
                                        .create().show();

                            case R.id.delete_account:
                                resetAlert.setTitle("Delete Account Permanently ?")
                                        .setMessage("Are You Sure ?")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                FirebaseUser user = firebaseAuth.getCurrentUser();

                                                user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(MainActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();

                                                        firebaseAuth.signOut();

                                                        startActivity(new Intent(getApplicationContext(), Login.class));

                                                        finish();
                                                    }
                                                });
                                            }
                                        }).setNegativeButton("Cancel", null)
                                        .create()
                                        .show();
                        }

                        return true;
                    }
                });
            }
        });
    }

    
    
}