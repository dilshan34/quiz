// QuestionController exposes REST endpoints for question management and scoring.
// It delegates business logic to QuestionService and handles HTTP request/response mapping.
// Update endpoints and logic as requirements evolve.
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

    // Injects the QuestionService to delegate business logic
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Endpoint to get all questions in the system.
     * @return list of all questions
     */
    @GetMapping(path = "/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    /**
     * Endpoint to add a new question.
     * @param question the question to add
     * @return HTTP 201 Created if successful
     */
    @PostMapping(path = "/addQuestion")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    /**
     * Endpoint to get question IDs by category and count.
     * @param category the question category
     * @param count number of questions to retrieve
     * @return list of question IDs
     */
    @GetMapping(path = "/category")
    public ResponseEntity<List<Integer>> findQuestionByCategory(@RequestParam String category, @RequestParam int count) {
        return questionService.getQuestionByCategory(category, count);
    }

    /**
     * Endpoint to calculate the score based on user responses.
     * @param response list of user responses
     * @return number of correct answers
     */
    @PostMapping(path = "/score")
    public ResponseEntity<Integer> getScore(@RequestBody List<UserResponse> response) {
        return new ResponseEntity<>(questionService.getCorrectAnswersCount(response), HttpStatus.OK);
    }

    /**
     * Endpoint to get questions by their IDs.
     * @param ids list of question IDs
     * @return list of question wrappers
     */
    @GetMapping(path = "/get")
    public ResponseEntity<List<QuestionWrapper>> getQuestionByID(@RequestParam List<Integer> ids) {
        return new ResponseEntity<>(questionService.getQuestionsByID(ids), HttpStatus.OK);
    }
}
