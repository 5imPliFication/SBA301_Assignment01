package fpt.he190091.assignment01.dtos;

import lombok.Data;

import java.util.List;

@Data
public class NewsArticleResponse {
    private Long newsId;
    private String newsTitle;
    private String headline;
    private String newsContent;
    private String newsSource;
    private Boolean newsStatus;

    private Long categoryId;
    private List<Long> tagIds;
    private Long authorId;
}
