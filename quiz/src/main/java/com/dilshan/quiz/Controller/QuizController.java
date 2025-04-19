package com.dilshan.quiz.Controller;

import com.dilshan.quiz.Model.QuestionWrapper;
import com.dilshan.quiz.Model.QuizDTO;
import com.dilshan.quiz.Model.UserResponse;
import com.dilshan.quiz.Service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    //    @PostMapping(path = "/create")
//    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int noOfQuestions, @RequestParam String title) {
//        quizService.createQuiz(category,noOfQuestions,title);
//        return new ResponseEntity<>("Created", HttpStatus.CREATED);
//    }
    @PostMapping(path = "/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDTO quizDTO) {
        quizService.createQuiz(quizDTO.getCategory(),quizDTO.getNoOfQuestion(),quizDTO.getTitle());
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable int id) {
        return new ResponseEntity<>(quizService.getQuizByID(id), HttpStatus.OK);
    }

    @PostMapping(path = "/check")
    public ResponseEntity<Integer> getQuiz( @RequestBody List<UserResponse> response) {
        return new ResponseEntity<>(quizService.getCorrectAnswersCount( response), HttpStatus.OK);
    }
}
