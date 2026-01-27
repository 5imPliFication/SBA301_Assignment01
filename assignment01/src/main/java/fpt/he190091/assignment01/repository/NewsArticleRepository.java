package fpt.he190091.assignment01.repository;

import fpt.he190091.assignment01.entity.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsArticleRepository extends JpaRepository<NewsArticle,Long> {
}
