package com.dilshan.quiz.Service;

import com.dilshan.quiz.Model.Question;
import com.dilshan.quiz.Repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository){
        this.questionRepository=questionRepository;
    }

    public ResponseEntity<List<Question>> getAllQuestions(){
        return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<String> addQuestion(Question question){
         questionRepository.save(question);
        return new ResponseEntity<>("Success",HttpStatus.CREATED);
    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category){
        return new ResponseEntity<>( questionRepository.findByCategory(category),HttpStatus.OK);
    }
}
