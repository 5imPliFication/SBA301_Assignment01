package fpt.he190091.assignment01.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class NewsArticleRequest {
    private String newsTitle;
    private String headline;
    private String newsContent;
    private String newsSource;
    private Boolean newsStatus;
    private Long categoryId;
    private Set<Long> tagIds;
}
