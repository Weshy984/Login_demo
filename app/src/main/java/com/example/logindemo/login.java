package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    //initializing widgets
TextView textview,textview1;
EditText username,password;
Button login;
FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //binding
        textview=findViewById(R.id.textView4);
        textview1=findViewById(R.id.textView5);
        username=findViewById(R.id.editTextTextPersonName3);
        password=findViewById(R.id.editTextTextPassword2);
        login=findViewById(R.id.button2);

        fAuth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=username.getText().toString().trim();
                String pass=password.getText().toString().trim();

                if(email.isEmpty()){
                    username.setError("No email entered");
                    username.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    username.setError("Wrong email format");
                    username.requestFocus();
                    return;
                }
                if(pass.isEmpty()){
                    password.setError("No password entered");
                    password.requestFocus();
                    return;
                }
                if(pass.length()<6){
                    password.setError("Characters should not be less than 6");
                    password.requestFocus();
                    return;
                }

                //authenticating user
                fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful()){
                          Toast.makeText(login.this,"Successfully logged in",Toast.LENGTH_SHORT).show();
                          startActivity(new Intent(login.this,MainActivity.class));
                      }  else {
                          Toast.makeText(login.this,"Wrong credentials" +task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                      }
                    }
                });
            }
        });

        textview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this,Register.class));
            }
        });
    }
}