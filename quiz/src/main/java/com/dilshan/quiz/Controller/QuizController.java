// QuizController exposes REST endpoints for quiz management (create, retrieve, check answers).
// It delegates business logic to QuizService and handles HTTP request/response mapping.
// Update endpoints and logic as requirements evolve.
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

    // Injects the QuizService to delegate business logic
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }
    
    /**
     * Endpoint to create a new quiz.
     * @param quizDTO the quiz details (category, number of questions, title)
     * @return HTTP 201 Created if successful
     */
    @PostMapping(path = "/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDTO quizDTO) {
        quizService.createQuiz(quizDTO.getCategory(),quizDTO.getNoOfQuestion(),quizDTO.getTitle());
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    /**
     * Endpoint to retrieve questions for a quiz by its ID.
     * @param id the quiz ID
     * @return list of questions for the quiz
     */
    @GetMapping(path = "/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable int id) {
        return new ResponseEntity<>(quizService.getQuizByID(id), HttpStatus.OK);
    }

    /**
     * Endpoint to check user answers and get the score.
     * @param response list of user responses
     * @return number of correct answers
     */
    @PostMapping(path = "/check")
    public ResponseEntity<Integer> getQuiz( @RequestBody List<UserResponse> response) {
        return new ResponseEntity<>(quizService.getCorrectAnswersCount( response), HttpStatus.OK);
    }
}
