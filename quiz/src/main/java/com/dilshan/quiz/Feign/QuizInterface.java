package com.dilshan.quiz.Feign;

import com.dilshan.quiz.Model.QuestionWrapper;
import com.dilshan.quiz.Model.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("question-service")
public interface QuizInterface {

    @GetMapping(path = "/question/category")
    public ResponseEntity<List<Integer>> findQuestionByCategory(@RequestParam String category, @RequestParam int count);


    @PostMapping(path = "/question/score")
    public ResponseEntity<Integer> getScore( @RequestBody List<UserResponse> response) ;

    @GetMapping(path = "/question/get")
    public ResponseEntity<List<QuestionWrapper>> getQuestionByID(@RequestParam List<Integer> ids);
}
