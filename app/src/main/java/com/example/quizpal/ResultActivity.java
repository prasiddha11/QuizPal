package com.example.quizpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.quizpal.databinding.ActivityResultBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultActivity extends AppCompatActivity {
    private Button restartBtn;
    private Button exitBtn;

    ActivityResultBinding binding;
    int POINTS=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int correctAnswers=getIntent().getIntExtra("correct",0);
        int totalQuestions=getIntent().getIntExtra("total",0);

        long points = correctAnswers * POINTS;
        binding.scoreBtn.setText(String.format("%d/%d",correctAnswers,totalQuestions));
        binding.earnedCoins.setText(String.valueOf(points));
        FirebaseFirestore database=FirebaseFirestore.getInstance();
        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(points));

        restartBtn=findViewById(R.id.restartBtn);
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ResultActivity.this, HomeFragment.class));
            }
        });

        exitBtn=findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }
}