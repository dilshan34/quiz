// QuestionService handles the business logic for question management and scoring.
// It interacts with the QuestionRepository for database operations.
// All exceptions and validations should provide clear error messages for the API layer.
package com.dilshan.question.Service;

import com.dilshan.question.Model.Question;
import com.dilshan.question.Model.QuestionWrapper;
import com.dilshan.question.Model.UserResponse;
import com.dilshan.question.Repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    // Injects the QuestionRepository for database access
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository){
        this.questionRepository=questionRepository;
    }

    /**
     * Retrieves all questions from the database.
     * @return list of all questions
     */
    public ResponseEntity<List<Question>> getAllQuestions(){
        return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Adds a new question to the database.
     * @param question the question to add
     * @return HTTP 201 Created if successful
     */
    public ResponseEntity<String> addQuestion(Question question){
        if (question == null) {
            throw new IllegalArgumentException("Question must not be null. Please provide question details.");
        }
        if (question.getQuestionTitle() == null || question.getQuestionTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Question title must not be empty. Please provide a title.");
        }
        if (question.getOption1() == null || question.getOption1().trim().isEmpty() ||
            question.getOption2() == null || question.getOption2().trim().isEmpty() ||
            question.getOption3() == null || question.getOption3().trim().isEmpty() ||
            question.getOption4() == null || question.getOption4().trim().isEmpty()) {
            throw new IllegalArgumentException("All options must be provided and not empty.");
        }
        if (question.getRightAnswer() == null || question.getRightAnswer().trim().isEmpty()) {
            throw new IllegalArgumentException("Right answer must not be empty. Please provide the correct answer.");
        }
        questionRepository.save(question);
        return new ResponseEntity<>("Success",HttpStatus.CREATED);
    }

    /**
     * Retrieves question IDs by category and count.
     * @param category the question category
     * @param noOfQuestions number of questions to retrieve
     * @return list of question IDs
     */
    public ResponseEntity<List<Integer>> getQuestionByCategory(String category,int noOfQuestions){
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category must not be empty. Please provide a valid category.");
        }
        if (noOfQuestions <= 0) {
            throw new IllegalArgumentException("Number of questions must be greater than zero. Please provide a valid number.");
        }
        return new ResponseEntity<>( questionRepository.getQuestionsByCategory(category,noOfQuestions),HttpStatus.OK);
    }

    /**
     * Calculates the number of correct answers from user responses.
     * @param response list of user responses
     * @return number of correct answers
     */
    public Integer getCorrectAnswersCount(List<UserResponse> response) {
        if (response == null || response.isEmpty()) {
            throw new IllegalArgumentException("User responses must not be empty. Please submit at least one answer.");
        }
        List<Question> questions =  questionRepository.findAll();
        Map<Integer,String> mapQuestions = questions.stream()
                .collect(Collectors.toMap(Question::getId,Question::getRightAnswer));
        int correctAnswers = 0;
        for(UserResponse x : response){
            String correctAnswer = mapQuestions.get(x.getId());
            if (correctAnswer!= null && correctAnswer.equals(x.getResponse())) {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    /**
     * Retrieves questions by their IDs and wraps them for the user.
     * @param ids list of question IDs
     * @return list of question wrappers
     */
    public List<QuestionWrapper> getQuestionsByID(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("Question IDs must not be empty. Please provide at least one ID.");
        }
        List<Question> questions =  questionRepository.findByQuestionID(ids);
        List<QuestionWrapper> userQuestions = new ArrayList<>();
        for(Question q : questions){
            QuestionWrapper questionWrapper = new QuestionWrapper(
                    q.getId(),
                    q.getQuestionTitle(),
                    q.getOption1(),
                    q.getOption2(),
                    q.getOption3(),
                    q.getOption4()
            );
            userQuestions.add(questionWrapper);
        }
        return userQuestions;
    }
}
