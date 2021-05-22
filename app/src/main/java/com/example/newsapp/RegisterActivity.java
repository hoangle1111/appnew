package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity  {

    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    private EditText editTextName, editTextConfirm, editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    private Button btnRegister;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();

        editTextName = findViewById(R.id.inputUsername);
        editTextEmail = findViewById(R.id.Email);
        editTextPassword = findViewById(R.id.Password);
        editTextConfirm = findViewById(R.id.inputConformPassword);
        btnRegister = findViewById(R.id.btnRegister);

        progressBar = findViewById(R.id.progressBarr);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    RegisterUser();
            }
        });
        TextView btn=findViewById(R.id.alreadyHaveAccount);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }

    private void RegisterUser() {
        String Name = editTextName.getText().toString();
        String Email =  editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();
        String ConfirmPassword = editTextConfirm.getText().toString();


        if (Name.isEmpty()){
            editTextName.setError("Không thể bỏ trống");
            editTextName.requestFocus();

        }
        if (Email.isEmpty()){
            editTextEmail.setError("Không thể bỏ trống");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            editTextEmail.setError("Xin hãy nhập email đúng");
            editTextEmail.requestFocus();
            return;

        }
        if (Password.isEmpty()&&Password.length()<6){
            editTextPassword.setError("Mật khẩu không thể bỏ trống và phải dài hơn 6 kí tự");
            editTextEmail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.getInstance().createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            user.setEmail(Email);
                            user.setName(Name);
                            myRef.child("User").push()
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this,"Succesful",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else {
                                        Toast.makeText(RegisterActivity.this,"Failed, Try again",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        }
                        else {
                            Toast.makeText(RegisterActivity.this,"Failed, Try againnnnnn",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            finish();
                        }
                    }
                });
    }
}
