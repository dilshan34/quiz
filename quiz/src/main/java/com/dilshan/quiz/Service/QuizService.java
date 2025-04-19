package com.dilshan.quiz.Service;

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

    public QuizService(QuizRepository quizRepository,QuizInterface quizInterface) {
        this.quizRepository = quizRepository;
        this.quizInterface=quizInterface;
    }

    public void createQuiz(String category, int noOfQuestions, String title) {

        //get question ids by number of questions and category
        List<Integer> questions = quizInterface.findQuestionByCategory(category,noOfQuestions).getBody();

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

    //return question by quiz id
    public List<QuestionWrapper> getQuizByID(int id) {

            //get questionIds by quiz id from quiz table
            List<Integer> questionIDs = quizRepository.getQuestionIds(id);
            //get questions from question service

        return quizInterface.getQuestionByID(questionIDs).getBody();
    }

    //get correct answers score
    public int getCorrectAnswersCount( List<UserResponse> responses){

        return quizInterface.getScore(responses).getBody();

    }

}
