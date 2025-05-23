package com.dilshan.quiz.Controller;

import com.dilshan.quiz.Model.QuestionWrapper;
import com.dilshan.quiz.Model.QuizDTO;
import com.dilshan.quiz.Model.UserResponse;
import com.dilshan.quiz.Service.QuizService;
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

class QuizControllerTest {

    @Mock
    private QuizService quizService;

    @InjectMocks
    private QuizController quizController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateQuiz() {
        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setCategory("Math");
        quizDTO.setNoOfQuestion(5);
        quizDTO.setTitle("Sample Quiz");

        doNothing().when(quizService).createQuiz(anyString(), anyInt(), anyString());
        ResponseEntity<String> response = quizController.createQuiz(quizDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Created", response.getBody());
        verify(quizService, times(1)).createQuiz("Math", 5, "Sample Quiz");
    }

    @Test
    void testGetQuiz() {
        int quizId = 1;
        // Provide all required arguments for QuestionWrapper constructor
        QuestionWrapper question = new QuestionWrapper(1, "Sample Title", "A", "B", "C", "D");
        List<QuestionWrapper> questions = Collections.singletonList(question);
        when(quizService.getQuizByID(quizId)).thenReturn(questions);
        ResponseEntity<List<QuestionWrapper>> response = quizController.getQuiz(quizId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(questions, response.getBody());
        verify(quizService, times(1)).getQuizByID(quizId);
    }

    @Test
    void testGetQuizCheck() {
        List<UserResponse> responses = Arrays.asList(new UserResponse(), new UserResponse());
        when(quizService.getCorrectAnswersCount(responses)).thenReturn(2);
        ResponseEntity<Integer> response = quizController.getQuiz(responses);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody());
        verify(quizService, times(1)).getCorrectAnswersCount(responses);
    }
}
