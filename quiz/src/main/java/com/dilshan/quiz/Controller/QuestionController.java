package com.dilshan.quiz.Controller;

import com.dilshan.quiz.Model.Question;
import com.dilshan.quiz.Service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/question")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService){
        this.questionService=questionService;
    }

    @GetMapping(path = "/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @PostMapping(path = "/addQuestion")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    @GetMapping(path = "/category/{category}")
    public ResponseEntity<List<Question>> findQuestionByCategory(@PathVariable String category){
        return questionService.getQuestionByCategory(category);
    }
}
