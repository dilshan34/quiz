package com.dilshan.quiz.Model;

public class QuizDTO {

    private String category;
    private int noOfQuestion;
    private String title;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNoOfQuestion() {
        return noOfQuestion;
    }

    public void setNoOfQuestion(int noOfQuestion) {
        this.noOfQuestion = noOfQuestion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public QuizDTO(String category, int noOfQuestion, String title) {
        this.category = category;
        this.noOfQuestion = noOfQuestion;
        this.title = title;
    }

    public QuizDTO(){}
}
