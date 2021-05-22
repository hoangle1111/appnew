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

public class LoginActivity extends AppCompatActivity {
     EditText editTextEmail,editTextPassword;
     Button btnLogin;
     FirebaseAuth mAuth;
     ProgressBar progressBar;

    TextView btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        btn=findViewById(R.id.textViewSignUp);

        editTextEmail= findViewById(R.id.Email);
        editTextPassword= findViewById(R.id.Password);
        progressBar=findViewById(R.id.progressBarr);
        btnLogin=findViewById(R.id.btnlogin);
        mAuth= FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String email = editTextEmail.getText().toString();
                    String password = editTextPassword.getText().toString();
                    if (email.isEmpty()){
                        editTextEmail.setError("Không thể bỏ trống");
                        editTextEmail.requestFocus();
                    }
                    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        editTextEmail.setError("Xin hãy nhập email đúng");
                        editTextEmail.requestFocus();

                    }
                    if (password.isEmpty()&&password.length()<6){
                        editTextPassword.setError("Mật khẩu không thể bỏ trống và phải dài hơn 6 kí tự");
                        editTextEmail.requestFocus();
                    }
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();

                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }
}
