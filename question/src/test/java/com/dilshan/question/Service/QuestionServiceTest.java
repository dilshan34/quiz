package com.dilshan.question.Service;

import com.dilshan.question.Model.Question;
import com.dilshan.question.Model.UserResponse;
import com.dilshan.question.Repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceTest {

    @Mock
    QuestionRepository questionRepository;
    @InjectMocks
    QuestionService questionService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllQuestions_ReturnsQuestionListWithOK() {
        // Arrange
        List<Question> mockQuestions = Arrays.asList(
                new Question(1, "Cat1", "Easy", "A", "B", "C", "D", "What is 1+1?", "B"),
                new Question(2, "Cat2", "Medium", "True", "False", "Maybe", "None", "Java is?", "True")
        );
        when(questionRepository.findAll()).thenReturn(mockQuestions);

        // Act
        ResponseEntity<List<Question>> response = questionService.getAllQuestions();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(questionRepository, times(1)).findAll();
    }

    @Test
    void addQuestionSuccessfully() {
        Question addQuestion = new Question(1, "Cat1", "Easy", "A", "B", "C", "D", "What is 1+1?", "B");

        when(questionRepository.save(addQuestion)).thenReturn(addQuestion);

        ResponseEntity<String> response = questionService.addQuestion(addQuestion);

        assertEquals(201,response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Success", response.getBody());
    }

    @Test
    void getQuestionByCategorySuccessfully() {

        String category = "java";
        int noOfQuestions = 4;

        List<Integer> questionIds = Arrays.asList(1,8,7,11);

        when(questionRepository.getQuestionsByCategory(category,noOfQuestions)).thenReturn(questionIds);

        ResponseEntity<List<Integer>> response = questionService.getQuestionByCategory(category,noOfQuestions);

        assertEquals(200,response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(questionIds,response.getBody());
        assertEquals(noOfQuestions,response.getBody().size());
    }

    @Test
    void getCorrectAnswersCountSuccessfully() {

        List<UserResponse> data = List.of(
                new UserResponse("B", 1)
        );
        List<Question> mockQuestions = Arrays.asList(
                new Question(1, "Cat1", "Easy", "A", "B", "C", "D", "What is 1+1?", "B"),
                new Question(2, "Cat2", "Medium", "True", "False", "Maybe", "None", "Java is?", "True")
        );
        when(questionRepository.findAll()).thenReturn(mockQuestions);

        Integer response = questionService.getCorrectAnswersCount(data);

        assertNotNull(response);
        assertEquals(1,response);

    }

    @Test
    void getQuestionsByID() {
    }
}