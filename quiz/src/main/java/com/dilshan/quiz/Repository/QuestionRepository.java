package com.dilshan.quiz.Repository;

import com.dilshan.quiz.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "SELECT * FROM Question q where q.category = :category limit :noOfQuestions", nativeQuery = true)
    List<Question> getQuestionsByCategory(String category, int noOfQuestions);

    @Query("SELECT q FROM Quiz z JOIN z.questions q WHERE z.id = :tId")
    List<Question> findByTitleID(@Param("tId") int tId);

}
