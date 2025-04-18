package com.dilshan.question.Controller;

import com.dilshan.question.Model.Question;
import com.dilshan.question.Model.QuestionWrapper;
import com.dilshan.question.Model.UserResponse;
import com.dilshan.question.Service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/score")
    public ResponseEntity<Integer> getScore( @RequestBody List<UserResponse> response) {
        return new ResponseEntity<>(questionService.getCorrectAnswersCount(response), HttpStatus.OK);
    }

    @GetMapping(path = "/get")
    public ResponseEntity<List<QuestionWrapper>> getQuestionByID(@RequestParam List<Integer> ids){
        return new ResponseEntity<>(questionService.getQuestionsByID(ids),HttpStatus.OK);
    }
}
