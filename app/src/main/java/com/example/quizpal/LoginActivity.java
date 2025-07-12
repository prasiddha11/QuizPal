package com.example.quizpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.quizpal.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        dialog=new ProgressDialog(this);
        dialog.setMessage("Logging into your Account...");

//        if (auth.getCurrentUser() != null) {
//            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//            startActivity(intent);
//        }

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,pass;
                email=binding.emailBox1.getText().toString();
                pass=binding.passwordBox1.getText().toString();

                dialog.show();

                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Logged In Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }
                        else {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        binding.createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}