package com.example.firebasecrudoperation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText EditLoginUsername, EditLoginPassword;
    private Button LoginButton;
    private TextView RegistrationText;
    private ProgressBar LoginProgressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditLoginUsername =findViewById(R.id.EditLoginUsername);
        EditLoginPassword = findViewById(R.id.EditLoginPassword);

        LoginButton = findViewById(R.id.Loginbutton);

        RegistrationText = findViewById(R.id.RegistrationText);
        LoginProgressBar=findViewById(R.id.LoginProgressBar);
        mAuth=FirebaseAuth.getInstance();

        RegistrationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginProgressBar.setVisibility(View.VISIBLE);
                String username= EditLoginUsername.getText().toString();
                String password = EditLoginPassword.getText().toString();
                if(TextUtils.isEmpty(username) && TextUtils.isEmpty(password))
                {
                    Toast.makeText(LoginActivity.this, "Enter your Username and Password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                LoginProgressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                Intent i= new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else
                            {
                                LoginProgressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Need To Login", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null)
        {
            Intent i=new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            this.finish();
        }

    }
}