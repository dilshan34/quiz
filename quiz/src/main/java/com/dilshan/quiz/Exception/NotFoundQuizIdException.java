package com.dilshan.quiz.Exception;

public class NotFoundQuizIdException extends RuntimeException{

    public NotFoundQuizIdException(String msg){
        super(msg);
    }
}
