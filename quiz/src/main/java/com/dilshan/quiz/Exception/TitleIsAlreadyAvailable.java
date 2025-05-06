package com.dilshan.quiz.Exception;

public class TitleIsAlreadyAvailable extends RuntimeException {
    public TitleIsAlreadyAvailable(String s) {
        super(s);
    }
}
