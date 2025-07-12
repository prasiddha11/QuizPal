package com.example.quizpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizpal.databinding.ActivityQuizBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.admin.v1.Index;

import java.util.ArrayList;
import java.util.Random;

public class quizActivity extends AppCompatActivity {
    ActivityQuizBinding binding;
    ArrayList<Question> questions;
    int index = 0;
    Question question;
    CountDownTimer timer;
    FirebaseFirestore database;
    int correctAnswers=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database=FirebaseFirestore.getInstance();

        questions=new ArrayList<>();

        final String catId = getIntent().getStringExtra("catId");
        Random random=new Random();
        final int rand=random.nextInt(12);
        database.collection("categories")
                .document(catId)
                .collection("questions")
                .whereGreaterThanOrEqualTo("index",rand)
                .orderBy("index")
                .limit(7).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.getDocuments().size() < 5) {
                            database.collection("categories")
                                    .document(catId)
                                    .collection("questions")
                                    .whereLessThanOrEqualTo("index", rand)
                                    .orderBy("index")
                                    .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                                Question question = snapshot.toObject(Question.class);
                                                questions.add(question);

                                            }
                                            setNextQuestions();
                                        }
                                    });

                        } else {
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                Question question = snapshot.toObject(Question.class);
                                questions.add(question);
                            }
                            setNextQuestions();
                        }
                    }
                });




        resetTimer();

    }

    void showAnswer() {
        if (question.getAnswer().equals(binding.option1.getText().toString()))
            binding.option1.setBackground(getResources().getDrawable(R.drawable.button2));
        else if (question.getAnswer().equals(binding.option2.getText().toString()))
            binding.option2.setBackground(getResources().getDrawable(R.drawable.button2));
        else if  (question.getAnswer().equals(binding.option3.getText().toString()))
            binding.option3.setBackground(getResources().getDrawable(R.drawable.button2));
        else if (question.getAnswer().equals(binding.option4.getText().toString()))
            binding.option4.setBackground(getResources().getDrawable(R.drawable.button2));

    }

    void setNextQuestions() {
        if (timer != null) {
            timer.cancel();
        }
        timer.start();
        if (index < questions.size()) {
            binding.questionCounter.setText(String.format("%d/%d" , (index+1), questions.size()));
            question = questions.get(index);
            binding.question.setText(question.getQuestion());
            binding.option1.setText(question.getOption1());
            binding.option2.setText(question.getOption2());
            binding.option3.setText(question.getOption3());
            binding.option4.setText(question.getOption4());
        }


    }
    void resetTimer() {
        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timer.setText(String.valueOf(millisUntilFinished/1000));

            }

            @Override
            public void onFinish() {

            }
        };
    }
    void checkAnswer(TextView textView) {
        String selectedAnswer = textView.getText().toString();
        if (selectedAnswer.equals(question.getAnswer())) {
            correctAnswers++;
            textView.setBackground(getResources().getDrawable(R.drawable.button2));
        }
        else {
            showAnswer();
            textView.setBackground(getResources().getDrawable(R.drawable.button1));
        }
    }
    void  reset(){
        binding.option1.setBackground(getResources().getDrawable(R.drawable.button_outline));
        binding.option2.setBackground(getResources().getDrawable(R.drawable.button_outline));
        binding.option3.setBackground(getResources().getDrawable(R.drawable.button_outline));
        binding.option4.setBackground(getResources().getDrawable(R.drawable.button_outline));
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.option_1:
            case R.id.option_2:
            case R.id.option_3:
            case R.id.option_4:
                if (timer != null) {
                    timer.cancel();
                }
                TextView selected = (TextView) view;
                checkAnswer(selected);

                break;
            case R.id.nextBtn:
                reset();
                if (index < questions.size()) {
                    index++;
                    setNextQuestions();
        } else {
                    Intent intent=new Intent(quizActivity.this,ResultActivity.class);
                    intent.putExtra("correct",correctAnswers);
                    intent.putExtra("total",questions.size());
                    startActivity(intent);

            Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();

        }
                break;
        }
    }
}