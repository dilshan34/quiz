package com.dilshan.quiz.Controller;

import com.dilshan.quiz.Model.Question;
import com.dilshan.quiz.Model.QuestionWrapper;
import com.dilshan.quiz.Model.Quiz;
import com.dilshan.quiz.Model.UserResponse;
import com.dilshan.quiz.Service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/quiz")
public class QuizController {

      private QuizService quizService;

     public QuizController(QuizService quizService){
         this.quizService = quizService;
     }

    @PostMapping(path = "/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int noOfQuestions, @RequestParam String title) {
        quizService.createQuiz(category,noOfQuestions,title);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable int id) {
        return new ResponseEntity<>(quizService.getQuizByID(id), HttpStatus.OK);
    }

    @PostMapping(path = "/check/{id}")
    public ResponseEntity<Integer> getQuiz(@PathVariable int id, @RequestBody List<UserResponse> response) {
        return new ResponseEntity<>(quizService.getCorrectAnswersCount(id,response), HttpStatus.OK);
    }
}
