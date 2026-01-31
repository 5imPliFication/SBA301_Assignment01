package fpt.he190091.assignment01.repository;

import fpt.he190091.assignment01.entity.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsArticleRepository extends JpaRepository<NewsArticle,Long> {
    @Query("SELECT DISTINCT n FROM NewsArticle n " +
            "LEFT JOIN FETCH n.category " +
            "LEFT JOIN FETCH n.createdBy " +
            "LEFT JOIN FETCH n.tags")
    List<NewsArticle> findAllWithRelations();

    @Query("SELECT n FROM NewsArticle n " +
            "LEFT JOIN FETCH n.category " +
            "LEFT JOIN FETCH n.createdBy " +
            "LEFT JOIN FETCH n.tags " +
            "WHERE n.newsArticleID = :id")
    Optional<NewsArticle> findByIdWithRelations(@Param("id") Long id);
}
