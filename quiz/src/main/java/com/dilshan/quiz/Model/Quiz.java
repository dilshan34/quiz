package com.dilshan.quiz.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
//    @ManyToMany
//    @JoinTable(
//            name = "quiz_questions",
//            joinColumns = @JoinColumn(name = "quiz_id"),
//            inverseJoinColumns = @JoinColumn(name = "question_id")
//    )
    @Transient
    private List<Integer> questionsIDs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getQuestionsIDs() {
        return questionsIDs;
    }

    public void setQuestionsIDs(List<Integer> questionsIDs) {
        this.questionsIDs = questionsIDs;
    }

    public Quiz(int id, String title, List<Integer> questionsIDs) {
        this.id = id;
        this.title = title;
        this.questionsIDs = questionsIDs;
    }
    public Quiz(){}
}
