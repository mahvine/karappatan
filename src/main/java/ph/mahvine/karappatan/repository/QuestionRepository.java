package ph.mahvine.karappatan.repository;

import ph.mahvine.karappatan.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Question entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = "select distinct krptn_question from Question krptn_question left join fetch krptn_question.answers",
        countQuery = "select count(distinct krptn_question) from Question krptn_question")
    Page<Question> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct krptn_question from Question krptn_question left join fetch krptn_question.answers")
    List<Question> findAllWithEagerRelationships();

    @Query("select krptn_question from Question krptn_question left join fetch krptn_question.answers where krptn_question.id =:id")
    Optional<Question> findOneWithEagerRelationships(@Param("id") Long id);

}
