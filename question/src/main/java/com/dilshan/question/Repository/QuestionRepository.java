package com.dilshan.question.Repository;

import com.dilshan.question.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "SELECT * FROM Question q where q.category = :category limit :noOfQuestions", nativeQuery = true)
    List<Question> getQuestionsByCategory(String category, int noOfQuestions);

    @Query(value = "SELECT * FROM Question q where q.id IN :ids", nativeQuery = true)
    List<Question> findByQuestionID(List<Integer> ids);
}
