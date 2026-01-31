package fpt.he190091.assignment01.dtos;

import lombok.Data;

import java.util.List;

@Data
public class TagResponse {
    private Long tagId;
    private String tagName;
    private String note;
    private List<Long> newsArticleIds;
}
