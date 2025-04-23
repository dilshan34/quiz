package com.dilshan.quiz.Exception;

public class NotFoundQuestionException extends RuntimeException{
    public NotFoundQuestionException(String msg){
        super(msg);
    }
}
