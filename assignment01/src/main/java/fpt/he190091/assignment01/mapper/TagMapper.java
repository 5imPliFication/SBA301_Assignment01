package fpt.he190091.assignment01.mapper;

import fpt.he190091.assignment01.dtos.TagResponse;
import fpt.he190091.assignment01.entity.NewsArticle;
import fpt.he190091.assignment01.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagMapper {

    public TagResponse toResponse(Tag tag) {
        if (tag == null) return null;

        TagResponse res = new TagResponse();
        res.setTagId(tag.getTagID());
        res.setTagName(tag.getTagName());
        res.setNote(tag.getNote());

        if (tag.getNewsArticles() != null) {
            res.setNewsArticleIds(
                    tag.getNewsArticles()
                            .stream()
                            .map(NewsArticle::getNewsArticleID)
                            .toList()
            );
        } else {
            res.setNewsArticleIds(List.of());
        }

        return res;
    }

    public List<TagResponse> toResponseList(List<Tag> tags) {
        return tags.stream().map(this::toResponse).toList();
    }
}
