package com.dilshan.quiz.Service;

import com.dilshan.quiz.Exception.NotFoundQuestionException;
import com.dilshan.quiz.Exception.NotFoundQuizIdException;
import com.dilshan.quiz.Exception.TitleIsAlreadyAvailable;
import com.dilshan.quiz.Feign.QuizInterface;
import com.dilshan.quiz.Model.QuestionWrapper;
import com.dilshan.quiz.Model.Quiz;
import com.dilshan.quiz.Model.UserResponse;
import com.dilshan.quiz.Repository.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class QuizServiceTest {
    @Mock
    private QuizRepository quizRepository;
    @Mock
    private QuizInterface quizInterface;
    @InjectMocks
    private QuizService quizService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateQuiz_Success() {
        when(quizRepository.checkTitle("title")).thenReturn("");
        when(quizInterface.findQuestionByCategory("cat", 2)).thenReturn(ResponseEntity.ok(Arrays.asList(1, 2)));
        when(quizRepository.save(any(Quiz.class))).thenReturn(new Quiz(1, "title", null));
        doNothing().when(quizRepository).insertQuizQuestion(anyInt(), anyInt());
        quizService.createQuiz("cat", 2, "title");
        verify(quizRepository).save(any(Quiz.class));
        verify(quizRepository, times(2)).insertQuizQuestion(anyInt(), anyInt());
    }

    @Test
    void testCreateQuiz_TitleAlreadyExists() {
        when(quizRepository.checkTitle("title")).thenReturn("exists");
        // Prevent NullPointerException by stubbing findQuestionByCategory
        when(quizInterface.findQuestionByCategory(anyString(), anyInt())).thenReturn(ResponseEntity.ok(Collections.emptyList()));
        Exception ex = assertThrows(TitleIsAlreadyAvailable.class, () -> quizService.createQuiz("cat", 2, "title"));
        assertEquals("Provided title already available, please enter a new title", ex.getMessage());
    }

    @Test
    void testCreateQuiz_NoQuestionsFound() {
        when(quizRepository.checkTitle("title")).thenReturn("");
        when(quizInterface.findQuestionByCategory("cat", 2)).thenReturn(ResponseEntity.ok(Collections.emptyList()));
        Exception ex = assertThrows(NotFoundQuestionException.class, () -> quizService.createQuiz("cat", 2, "title"));
        assertEquals("Can't find any questions from this category, Invalid Category", ex.getMessage());
    }

    @Test
    void testGetQuizByID_Success() {
        when(quizRepository.getQuestionIds(1)).thenReturn(Arrays.asList(1, 2));
        List<QuestionWrapper> questions = Arrays.asList(
            new QuestionWrapper(1, "Q1", "A", "B", "C", "D"),
            new QuestionWrapper(2, "Q2", "A", "B", "C", "D")
        );
        when(quizInterface.getQuestionByID(Arrays.asList(1, 2))).thenReturn(ResponseEntity.ok(questions));
        List<QuestionWrapper> result = quizService.getQuizByID(1);
        assertEquals(2, result.size());
        assertEquals("Q1", result.get(0).getTitle());
    }

    @Test
    void testGetQuizByID_NotFound() {
        when(quizRepository.getQuestionIds(1)).thenReturn(Collections.emptyList());
        Exception ex = assertThrows(NotFoundQuizIdException.class, () -> quizService.getQuizByID(1));
        assertEquals("There is no such Quiz", ex.getMessage());
    }

    @Test
    void testGetCorrectAnswersCount() {
        List<UserResponse> responses = Arrays.asList(new UserResponse(), new UserResponse());
        when(quizInterface.getScore(responses)).thenReturn(ResponseEntity.ok(2));
        int score = quizService.getCorrectAnswersCount(responses);
        assertEquals(2, score);
    }

    @Test
    void testCreateQuiz_QuizRepositorySaveArguments() {
        when(quizRepository.checkTitle("title")).thenReturn("");
        when(quizInterface.findQuestionByCategory("cat", 2)).thenReturn(ResponseEntity.ok(Arrays.asList(1, 2)));
        when(quizRepository.save(any(Quiz.class))).thenReturn(new Quiz(1, "title", null));
        doNothing().when(quizRepository).insertQuizQuestion(anyInt(), anyInt());
        quizService.createQuiz("cat", 2, "title");
        verify(quizRepository).save(argThat(quiz -> quiz.getTitle().equals("title")));
    }

    @Test
    void testCreateQuiz_InsertQuizQuestionArguments() {
        when(quizRepository.checkTitle("title")).thenReturn("");
        when(quizInterface.findQuestionByCategory("cat", 2)).thenReturn(ResponseEntity.ok(Arrays.asList(10, 20)));
        when(quizRepository.save(any(Quiz.class))).thenReturn(new Quiz(5, "title", null));
        doNothing().when(quizRepository).insertQuizQuestion(anyInt(), anyInt());
        quizService.createQuiz("cat", 2, "title");
        verify(quizRepository).insertQuizQuestion(eq(5), eq(10));
        verify(quizRepository).insertQuizQuestion(eq(5), eq(20));
    }

    @Test
    void testGetCorrectAnswersCount_VariousScores() {
        List<UserResponse> responses = Arrays.asList(new UserResponse(), new UserResponse(), new UserResponse());
        when(quizInterface.getScore(responses)).thenReturn(ResponseEntity.ok(3));
        int score = quizService.getCorrectAnswersCount(responses);
        assertEquals(3, score);
    }

    @Test
    void testCreateQuiz_QuestionServiceReturnsNull() {
        when(quizRepository.checkTitle("title")).thenReturn("");
        when(quizInterface.findQuestionByCategory("cat", 2)).thenReturn(null);
        assertThrows(NullPointerException.class, () -> quizService.createQuiz("cat", 2, "title"));
    }

    @Test
    void testGetQuizByID_QuestionServiceReturnsNull() {
        when(quizRepository.getQuestionIds(1)).thenReturn(Arrays.asList(1, 2));
        when(quizInterface.getQuestionByID(Arrays.asList(1, 2))).thenReturn(null);
        assertThrows(NullPointerException.class, () -> quizService.getQuizByID(1));
    }
}
