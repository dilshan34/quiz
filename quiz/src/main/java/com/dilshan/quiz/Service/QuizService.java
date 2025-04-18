package com.dilshan.quiz.Service;

import com.dilshan.quiz.Model.Question;
import com.dilshan.quiz.Model.QuestionWrapper;
import com.dilshan.quiz.Model.Quiz;
import com.dilshan.quiz.Model.UserResponse;
import com.dilshan.quiz.Repository.QuestionRepository;
import com.dilshan.quiz.Repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuizService {

   final private QuizRepository quizRepository;
   final private QuestionRepository questionRepository;

    public QuizService(QuizRepository quizRepository,QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    public void createQuiz(String category, int noOfQuestions, String title) {

        List<Question> questions = questionRepository.getQuestionsByCategory(category,noOfQuestions);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        quizRepository.save(quiz);
    }

    public List<QuestionWrapper> getQuizByID(int id) {
            List<Question> questions =  questionRepository.findByTitleID(id);
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

    public int getCorrectAnswersCount(int id, List<UserResponse> responses){
        List<Question> questions =  questionRepository.findByTitleID(id);

        Map<Integer,String> mapQuestions = questions.stream()
                .collect(Collectors.toMap(Question::getId,Question::getRightAnswer));


        int correctAnswers = 0;
        for(UserResponse x : responses){
                String correctAnswer = mapQuestions.get(x.getId());
                if (correctAnswer!= null && correctAnswer.equals(x.getResponse())) {
                    correctAnswers++;
            }
        }
        return correctAnswers;

    }

}
