package fpt.he190091.assignment01.mapper;

import fpt.he190091.assignment01.dtos.NewsArticleResponse;
import fpt.he190091.assignment01.entity.NewsArticle;
import fpt.he190091.assignment01.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class NewsArticleMapper {

    public NewsArticleResponse toResponse(NewsArticle article) {
        if (article == null) return null;

        NewsArticleResponse res = new NewsArticleResponse();
        res.setNewsId(article.getNewsArticleID());
        res.setNewsTitle(article.getNewsTitle());
        res.setHeadline(article.getHeadline());
        res.setNewsContent(article.getNewsContent());
        res.setNewsSource(article.getNewsSource());
        res.setNewsStatus(article.getNewsStatus());
        res.setCreatedDate(article.getCreatedDate());
        res.setModifiedDate(article.getModifiedDate());

        // Safe category mapping
        if (article.getCategory() != null) {
            res.setCategoryId(article.getCategory().getCategoryID());
            res.setCategoryName(article.getCategory().getCategoryName());
        }

        // Safe author mapping
        if (article.getCreatedBy() != null) {
            res.setAuthorName(article.getCreatedBy().getAccountName());
        }

        // Tags mapping
        try {
            if (article.getTags() != null && !article.getTags().isEmpty()) {
                res.setTagIds(
                        article.getTags().stream()
                                .map(Tag::getTagID)
                                .toList()
                );
                res.setTagNames(
                        article.getTags().stream()
                                .map(Tag::getTagName)
                                .toList()
                );
            } else {
                res.setTagIds(List.of());
                res.setTagNames(List.of());
            }
        } catch (Exception e) {
            res.setTagIds(List.of());
            res.setTagNames(List.of());
        }

        return res;
    }

    public List<NewsArticleResponse> toResponseList(List<NewsArticle> articles) {
        if (articles == null) return List.of();
        return articles.stream()
                .map(this::toResponse)
                .toList();
    }
}
