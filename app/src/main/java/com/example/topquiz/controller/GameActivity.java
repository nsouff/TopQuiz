package com.example.topquiz.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.topquiz.R;
import com.example.topquiz.model.Question;
import com.example.topquiz.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mQuestionTextView;
    private Button[] activityGameAnswer = new Button[4];

    private Question mCurrentQuestion;
    private QuestionBank mQuestionBank;

    private int mScore;
    private int mNumberOfQuestions;

    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";

    private boolean mEnableTouchEvent;

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mNumberOfQuestions);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionBank = genereateQuestion();

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        }
        else {
            mNumberOfQuestions = 4;
            mScore = 0;
        }



        activityGameAnswer[0] = findViewById(R.id.activity_game_answer1_btn);
        activityGameAnswer[1] = findViewById(R.id.activity_game_answer2_btn);
        activityGameAnswer[2] = findViewById(R.id.activity_game_answer3_btn);
        activityGameAnswer[3] = findViewById(R.id.activity_game_answer4_btn);
        mQuestionTextView = findViewById(R.id.activity_game_question_text);

        for (int i = 0; i < activityGameAnswer.length; i++) {
            activityGameAnswer[i].setTag(i);
            activityGameAnswer[i].setOnClickListener(this);
        }

        mEnableTouchEvent = true;
        mCurrentQuestion = mQuestionBank.getQuestion();

        displayQuestion(mCurrentQuestion);


    }

    @Override
    public void onClick(View v) {
        mEnableTouchEvent = false;
        final int responseIndex = (int) v.getTag();
        activityGameAnswer[mCurrentQuestion.getmAnswerIndex()].setBackgroundColor(getResources().getColor(R.color.rightAnswer));
        if (responseIndex == mCurrentQuestion.getmAnswerIndex()) {
            Toast.makeText(this, "Right answer", Toast.LENGTH_SHORT).show();
            mScore++;
        }
        else {
            Toast.makeText(this, "Wrong answer", Toast.LENGTH_SHORT).show();
            activityGameAnswer[responseIndex].setBackgroundColor(getResources().getColor(R.color.wrongAnswer));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvent = true;
                if (--mNumberOfQuestions == 0) {
                    endGame();
                }
                else {
                    activityGameAnswer[responseIndex].setBackgroundColor(getResources().getColor(R.color.white));
                    activityGameAnswer[mCurrentQuestion.getmAnswerIndex()].setBackgroundColor(getResources().getColor(R.color.white));
                    mCurrentQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mCurrentQuestion);
                }
            }
        }, 2000);



    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvent && super.dispatchTouchEvent(ev);
    }

    private QuestionBank genereateQuestion() {
        Question question1 = new Question("Who is the creator of Android?",
                Arrays.asList("Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"),
                0);

        Question question2 = new Question("When did the first man land on the moon?",
                Arrays.asList("1958",
                        "1962",
                        "1967",
                        "1969"),
                3);

        Question question3 = new Question("What is the house number of The Simpsons?",
                Arrays.asList("42",
                        "101",
                        "666",
                        "742"),
                3);
        Question question4 = new Question("Who is the basketball GOAT?",
                Arrays.asList("Jordan",
                        "Kobe",
                        "LeBron",
                        "Alex Caruso"),
                1);
        Question question5 =  new Question("In what year was Google launched on the web?",
                Arrays.asList("1989",
                        "2000",
                        "1998",
                        "1995"),
                2);
        Question question6 = new Question("How many oscars did the Titanic movie got?",
                Arrays.asList("13",
                        "11",
                        "7",
                        "9"),
                1);

        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3,
                question4,
                question5,
                question6));
    }

    private void displayQuestion(Question question) {
        mQuestionTextView.setText(question.getmQuestion());
        for (int i = 0; i < activityGameAnswer.length; i++) {
            activityGameAnswer[i].setText(question.getmChoiceList().get(i));
        }
    }

    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Well done!")
                .setMessage("Your score is " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }
}
