package com.example.firebasecrudoperation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class RegistrationActivity extends AppCompatActivity {
    private TextInputEditText EditRegistrationUsername, EditRegistrationPassword, EditRegistrationConfirmPassword;
    private Button Registrationbutton;
    private ProgressBar RegistrationProgressBar;
    private TextView RegistrationLoginText;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        EditRegistrationUsername=findViewById(R.id.EditRegistrationUsername);
        EditRegistrationPassword=findViewById(R.id.EditRegistrationPassword);
        EditRegistrationConfirmPassword=findViewById(R.id.EditRegistrationConfirmPassword);
        Registrationbutton=findViewById(R.id.Registrationbutton);
        RegistrationLoginText=findViewById(R.id.RegistrationLoginText);
        RegistrationProgressBar=findViewById(R.id.RegistrationProgressBar);
        mAuth=FirebaseAuth.getInstance();

        RegistrationLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        Registrationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrationProgressBar.setVisibility(View.VISIBLE);
                String username=EditRegistrationUsername.getText().toString();
                String password=EditRegistrationPassword.getText().toString();
                String cnfpassword=EditRegistrationConfirmPassword.getText().toString();
                if(!password.equals(cnfpassword))
                {
                    RegistrationProgressBar.setVisibility(View.GONE);
                    Toast.makeText(RegistrationActivity.this, "Please check both password", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(username) && TextUtils.isEmpty(password) && TextUtils.isEmpty(cnfpassword))
                {
                    RegistrationProgressBar.setVisibility(View.GONE);
                    Toast.makeText(RegistrationActivity.this, "Cannot be Empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                RegistrationProgressBar.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, "User Registration Successfull", Toast.LENGTH_SHORT).show();
                                Intent i =new Intent(RegistrationActivity.this,LoginActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else{
                                RegistrationProgressBar.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, "Unsucessfull Failed to Register", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
    }
}