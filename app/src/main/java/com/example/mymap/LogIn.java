package com.example.mymap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {

    EditText txt_user;
    EditText txt_password;
    Button btn_login;
    FirebaseAuth loginAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        txt_user= findViewById(R.id.txt_usuario);
        txt_password= findViewById(R.id.txt_password);
        btn_login= findViewById(R.id.btn_login);

        loginAuth= FirebaseAuth.getInstance();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txt_user.getText().toString();
                String password = txt_password.getText().toString();

                signInUser(email,password);
            }
        });

    }

    private void signInUser(String email, String password) {
        if (validateForm()) {
            loginAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                    // Sign in success, update UI
                                FirebaseUser user = loginAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = loginAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser currentUser){
        if(currentUser!=null){
            Intent intent = new Intent(getBaseContext(), MapsActivity.class);
            intent.putExtra("user", currentUser.getEmail());
            startActivity(intent);
        } else {
            txt_user.setText("");
            txt_password.setText("");
        }
    }
    private boolean validateForm() {
        boolean valid = true;
        String email = txt_user.getText().toString();
        if (TextUtils.isEmpty(email)) {
            txt_user.setError("Required.");
            valid = false;
        } else {
            txt_user.setError(null);
        }
        String password = txt_password.getText().toString();
        if (TextUtils.isEmpty(password)) {
            txt_password.setError("Required.");
            valid = false;
        } else {
            txt_password.setError(null);
        }
        return valid;
    }
}
