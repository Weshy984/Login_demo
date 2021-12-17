package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class Register extends AppCompatActivity {
//Initializing
    TextView textView,textView2,textView3;
    EditText name,email,password,phone;
    Button register;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //binding
        textView=findViewById(R.id.textView);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        name=findViewById(R.id.editTextTextPersonName);
        email=findViewById(R.id.editTextTextPersonName2);
        password=findViewById(R.id.editTextTextPassword);
        phone=findViewById(R.id.editTextTextPersonName3);
        register=findViewById(R.id.button);
        fAuth=FirebaseAuth.getInstance();
        //check if user is already existing
        if(fAuth.getCurrentUser() !=null){
            startActivity(new Intent(Register.this,MainActivity.class));
            finish();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail= email.getText().toString().trim();
                String pass= password.getText().toString().trim();
                //check if email is input
                if(mail.isEmpty()){
                    email.setError("Email is empty");
                    email.requestFocus();
                    return;
                }
                //check if syntax of email is followed
                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    email.setError("Input valid email");
                    email.requestFocus();
                    return;
                }
                //check if password is empty
                if(pass.isEmpty()){
                    password.setError("No password");
                    password.requestFocus();
                    return;
                }
                //check password length
                if(pass.length()<6){
                    password.setError("password should be 6 or more characters");
                    password.requestFocus();
                    return;
                }

                //registering user into firebase
                fAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful()){
                             Toast.makeText(Register.this,"Successfully registered",Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(Register.this,MainActivity.class));
                         }else{
                             Toast.makeText(Register.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                         }
                    }
                });

            }
        });

        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,login.class));
            }
        });
    }

}