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

        if (article.getCategory() != null) {
            res.setCategoryId(article.getCategory().getCategoryID());
        }

        if (article.getTags() != null) {
            res.setTagIds(
                    article.getTags()
                            .stream()
                            .map(Tag::getTagID)
                            .toList()
            );
        } else {
            res.setTagIds(List.of());
        }

        if (article.getCreatedBy() != null) {
            res.setAuthorId(article.getCreatedBy().getAccountID());
        }

        return res;
    }

    public List<NewsArticleResponse> toResponseList(List<NewsArticle> articles) {
        return articles.stream().map(this::toResponse).toList();
    }
}
