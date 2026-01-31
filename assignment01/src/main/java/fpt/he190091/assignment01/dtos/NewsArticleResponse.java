package fpt.he190091.assignment01.dtos;

import lombok.Data;

import java.time.LocalDateTime;
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
    private String categoryName;
    private String authorName;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private List<Long> tagIds;
    private List<String> tagNames;
}
