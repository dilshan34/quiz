package com.dilshan.quiz.Repository;

import com.dilshan.quiz.Model.Quiz;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO quiz_questions (quiz_id, question_id) VALUES (:quizId, :questionId)", nativeQuery = true)
    void insertQuizQuestion(@Param("quizId") int quizId, @Param("questionId") int questionId);

    @Query(value = "select question_id from quiz_questions where quiz_id = :id",nativeQuery = true)
    List<Integer> getQuestionIds(@Param("id") int id);

    @Query(value = "select category from quiz where title = :title",nativeQuery = true)
    String checkTitle(String title);
}
