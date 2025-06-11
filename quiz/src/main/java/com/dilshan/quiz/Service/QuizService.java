// QuizService handles the business logic for quiz management, including creation, retrieval, and scoring.
// It interacts with the QuizRepository for database operations and QuizInterface for external service calls.
// All exceptions are custom and provide clear error messages for the API layer.
package com.dilshan.quiz.Service;

import com.dilshan.quiz.Exception.NotFoundQuestionException;
import com.dilshan.quiz.Exception.NotFoundQuizIdException;
import com.dilshan.quiz.Exception.TitleIsAlreadyAvailable;
import com.dilshan.quiz.Feign.QuizInterface;
import com.dilshan.quiz.Model.QuestionWrapper;
import com.dilshan.quiz.Model.Quiz;
import com.dilshan.quiz.Model.UserResponse;
import com.dilshan.quiz.Repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    final private QuizRepository quizRepository;
    private QuizInterface quizInterface;

    public QuizService(QuizRepository quizRepository, QuizInterface quizInterface) {
        this.quizRepository = quizRepository;
        this.quizInterface = quizInterface;
    }

    /**
     * Creates a new quiz with the given category, number of questions, and title.
     * Checks for duplicate titles and valid question availability.
     * Saves the quiz and its questions to the database.
     * Throws IllegalArgumentException for invalid input.
     */
    public void createQuiz(String category, int noOfQuestions, String title) {
        // Validate input
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category must not be empty. Please provide a valid category.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title must not be empty. Please provide a quiz title.");
        }
        if (noOfQuestions <= 0) {
            throw new IllegalArgumentException("Number of questions must be greater than zero. Please provide a valid number.");
        }

        // Check if a quiz with the same title already exists
        String existingTitle = quizRepository.checkTitle(title);

        //get question ids by number of questions and category
        List<Integer> questions = quizInterface.findQuestionByCategory(category, noOfQuestions).getBody();
        if (existingTitle != null && !existingTitle.isEmpty()) {
            // keep the original error message expected by tests
            throw new TitleIsAlreadyAvailable("Provided title already available, please enter a new title");
        }
        if(questions == null || questions.isEmpty()){
            throw new NotFoundQuestionException("No questions found for the selected category. Please choose a different category or add questions first.");
        }
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionsIDs(questions);

        //save quiz title to quiz table
        Quiz savedQuiz = quizRepository.save(new Quiz(0, quiz.getTitle(), null));

        // Save question IDs to quiz_questions
        for (Integer qId : quiz.getQuestionsIDs()) {
            quizRepository.insertQuizQuestion(savedQuiz.getId(), qId);
        }

    }

    /**
     * Retrieves the list of questions for a given quiz ID.
     * Throws NotFoundQuizIdException if the quiz does not exist.
     * Throws IllegalArgumentException for invalid input.
     */
    //return question by quiz id
    public List<QuestionWrapper> getQuizByID(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Quiz ID must be greater than zero. Please provide a valid quiz ID.");
        }
        //get questionIds by quiz id from quiz table
        List<Integer> questionIDs = quizRepository.getQuestionIds(id);
        //get questions from question service
        if (questionIDs == null || questionIDs.isEmpty()) {
            throw new NotFoundQuizIdException("No quiz found with the provided ID. Please check the quiz ID and try again.");
        }

        return quizInterface.getQuestionByID(questionIDs).getBody();
    }

    /**
     * Calculates the number of correct answers from user responses.
     * Delegates scoring to the question service via Feign client.
     * Throws IllegalArgumentException for invalid input.
     */
    //get correct answers score
    public int getCorrectAnswersCount(List<UserResponse> responses) {
        if (responses == null || responses.isEmpty()) {
            throw new IllegalArgumentException("User responses must not be empty. Please submit at least one answer.");
        }
        Integer score = quizInterface.getScore(responses).getBody();
        return score != null ? score : 0;
    }

}
