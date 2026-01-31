package fpt.he190091.assignment01.service;

import fpt.he190091.assignment01.dtos.NewsArticleRequest;
import fpt.he190091.assignment01.entity.NewsArticle;
import fpt.he190091.assignment01.entity.SystemAccount;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public interface NewsArticleService {
    List<NewsArticle> findAllNewsArticles();

    NewsArticle findById(Long id);

    NewsArticle createNewsArticle(NewsArticleRequest dto, SystemAccount creator);

    NewsArticle updateNewsArticle(Long id, NewsArticleRequest dto, SystemAccount updater);

    void deleteNewsArticle(Long id);
}
