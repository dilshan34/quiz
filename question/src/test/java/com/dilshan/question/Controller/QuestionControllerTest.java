package com.dilshan.question.Controller;

import com.dilshan.question.Model.Question;
import com.dilshan.question.Model.QuestionWrapper;
import com.dilshan.question.Model.UserResponse;
import com.dilshan.question.Service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionControllerTest {
    @Mock
    private QuestionService questionService;
    @InjectMocks
    private QuestionController questionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllQuestions() {
        List<Question> questions = Arrays.asList(new Question(), new Question());
        when(questionService.getAllQuestions()).thenReturn(ResponseEntity.ok(questions));
        ResponseEntity<List<Question>> response = questionController.getAllQuestions();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(questions, response.getBody());
    }

    @Test
    void testAddQuestion() {
        Question question = new Question();
        when(questionService.addQuestion(question)).thenReturn(new ResponseEntity<>("Success", HttpStatus.CREATED));
        ResponseEntity<String> response = questionController.addQuestion(question);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Success", response.getBody());
    }

    @Test
    void testFindQuestionByCategory() {
        List<Integer> ids = Arrays.asList(1, 2, 3);
        when(questionService.getQuestionByCategory("cat", 3)).thenReturn(ResponseEntity.ok(ids));
        ResponseEntity<List<Integer>> response = questionController.findQuestionByCategory("cat", 3);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ids, response.getBody());
    }

    @Test
    void testGetScore() {
        List<UserResponse> responses = Arrays.asList(new UserResponse(), new UserResponse());
        when(questionService.getCorrectAnswersCount(responses)).thenReturn(2);
        ResponseEntity<Integer> response = questionController.getScore(responses);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody());
    }

    @Test
    void testGetQuestionByID() {
        List<Integer> ids = Arrays.asList(1, 2);
        List<QuestionWrapper> wrappers = Arrays.asList(
            new QuestionWrapper(1, "Q1", "A", "B", "C", "D"),
            new QuestionWrapper(2, "Q2", "A", "B", "C", "D")
        );
        when(questionService.getQuestionsByID(ids)).thenReturn(wrappers);
        ResponseEntity<List<QuestionWrapper>> response = questionController.getQuestionByID(ids);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(wrappers, response.getBody());
    }
}
