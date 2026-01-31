package fpt.he190091.assignment01.service.serviceImpl;


import fpt.he190091.assignment01.dtos.TagRequest;
import fpt.he190091.assignment01.entity.Tag;
import fpt.he190091.assignment01.repository.TagRepository;
import fpt.he190091.assignment01.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepo;

    @Override
    public List<Tag> findAllTag() {
        return tagRepo.findAll();
    }

    @Override
    public Tag findById(Long id) {
        return tagRepo.findById(id).orElse(null);
    }

    @Override
    public Tag findByName(String name) {
        return tagRepo.findByTagName(name);
    }

    @Override
    @Transactional
    public Tag createTag(TagRequest dto) {
        Tag tag = new Tag();
        tag.setTagName(dto.getTagName());
        tag.setNote(dto.getNote());

        return tagRepo.save(tag);
    }

    @Override
    @Transactional
    public void deleteTag(Long id) {
        tagRepo.deleteById(id);
    }

    @Override
    @Transactional
    public Tag updateTag(Long id, TagRequest dto) {
        Tag tag = tagRepo.findById(id).orElse(null);
        if (tag == null) return null;

        if (dto.getTagName() != null)
            tag.setTagName(dto.getTagName());

        if (dto.getNote() != null)
            tag.setNote(dto.getNote());

        return tagRepo.save(tag);
    }
}
