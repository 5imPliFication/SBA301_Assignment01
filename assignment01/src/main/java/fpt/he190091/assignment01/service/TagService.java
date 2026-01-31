package fpt.he190091.assignment01.service;

import fpt.he190091.assignment01.dtos.TagRequest;
import fpt.he190091.assignment01.entity.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    List<Tag> findAllTag();

    Tag findById(Long id);

    Tag findByName(String name);

    Tag createTag(TagRequest tag);

    void deleteTag(Long id);

    Tag updateTag(Long id, TagRequest dto);
}
