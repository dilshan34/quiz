package com.dilshan.question.Repository;

import com.dilshan.question.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    // Use the correct table name `questions` to avoid SQL errors on case-sensitive databases
    @Query(value = "SELECT id FROM questions q WHERE q.category = :category LIMIT :noOfQuestions", nativeQuery = true)
    List<Integer> getQuestionsByCategory(String category, int noOfQuestions);

    @Query(value = "SELECT * FROM questions q WHERE q.id IN :ids", nativeQuery = true)
    List<Question> findByQuestionID(List<Integer> ids);
}
