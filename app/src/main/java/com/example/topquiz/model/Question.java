package com.example.topquiz.model;

import java.util.List;

/**
 * Created by Nathan Souffan on 27/03/20.
 */
public class Question {
    private String mQuestion;
    private List<String> mChoiceList;
    private int mAnswerIndex;

    public Question(String mQuestion, List<String> mChoiceList, int mAnswerIndex) {
        if (mChoiceList.size() == 0 || mAnswerIndex >= mChoiceList.size()) throw new IllegalArgumentException();

        this.mQuestion = mQuestion;
        this.mChoiceList = mChoiceList;
        this.mAnswerIndex = mAnswerIndex;
    }

    public String getmQuestion() {
        return mQuestion;
    }

    public List<String> getmChoiceList() {
        return mChoiceList;
    }

    public int getmAnswerIndex() {
        return mAnswerIndex;
    }
}
