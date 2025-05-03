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

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository){
        this.questionRepository=questionRepository;
    }

    public ResponseEntity<List<Question>> getAllQuestions(){
        return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<String> addQuestion(Question question){
         questionRepository.save(question);
        return new ResponseEntity<>("Success",HttpStatus.CREATED);
    }

    public ResponseEntity<List<Integer>> getQuestionByCategory(String category,int id){
        return new ResponseEntity<>( questionRepository.getQuestionsByCategory(category,id),HttpStatus.OK);
    }

    public Integer getCorrectAnswersCount(List<UserResponse> response) {

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

    public List<QuestionWrapper> getQuestionsByID(List<Integer> ids) {
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
