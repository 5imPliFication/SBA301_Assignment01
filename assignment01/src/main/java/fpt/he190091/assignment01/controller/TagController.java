package fpt.he190091.assignment01.controller;

import fpt.he190091.assignment01.dtos.TagRequest;
import fpt.he190091.assignment01.dtos.TagResponse;
import fpt.he190091.assignment01.entity.Tag;
import fpt.he190091.assignment01.mapper.TagMapper;
import fpt.he190091.assignment01.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final TagMapper mapper;

    @GetMapping
    public List<TagResponse> getAllTags(){
        return mapper.toResponseList(tagService.findAllTag());
    }

    @GetMapping("/{id}")
    public TagResponse getTagById(@PathVariable Long id){
        return mapper.toResponse(tagService.findById(id));
    }

    @GetMapping("/by-name/{name}")
    public TagResponse getTagByName(@PathVariable String name){
        return mapper.toResponse(tagService.findByName(name));
    }

    @PostMapping
    public TagResponse createTag(@RequestBody TagRequest tag){
        return mapper.toResponse(tagService.createTag(tag));
    }

    @PatchMapping("/update/{id}")
    public TagResponse updateTag(@RequestBody TagRequest tag, @PathVariable Long id){
        return mapper.toResponse(tagService.updateTag(id, tag));
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Long id){
        tagService.deleteTag(id);
    }
}
