package com.example.lenovo.ap2;

import java.io.Serializable;

public class fq implements Serializable {
    int id;
    String Question;
    Integer UserId;

    public void setQuestion(String question) {
        Question = question;
    }

    public void setUsername(int username) {
        UserId = username;
    }

    public String getQuestion() {

        return Question;
    }

    public int getUsername() {
        return UserId;
    }

    public fq() {

    }

    public fq(String question, int username) {

        Question = question;
        UserId = username;
    }
}
