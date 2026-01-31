package fpt.he190091.assignment01.controller;

import fpt.he190091.assignment01.dtos.NewsArticleRequest;
import fpt.he190091.assignment01.dtos.NewsArticleResponse;
import fpt.he190091.assignment01.entity.NewsArticle;
import fpt.he190091.assignment01.entity.SystemAccount;
import fpt.he190091.assignment01.mapper.NewsArticleMapper;
import fpt.he190091.assignment01.service.NewsArticleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/news")
@RequiredArgsConstructor
public class NewsArticleController {
    private final NewsArticleService articleService;
    private final NewsArticleMapper mapper;

    @GetMapping
    public List<NewsArticleResponse> getAll() {
        return mapper.toResponseList(articleService.findAllNewsArticles());
    }

    @GetMapping("/{id}")
    public NewsArticleResponse getById(@PathVariable Long id) {
        return mapper.toResponse(articleService.findById(id));
    }

    @PostMapping
    public NewsArticleResponse create(@RequestBody NewsArticleRequest dto,
                              HttpSession session) {
        System.out.println("News Article HIT!!!");
        SystemAccount user =
                (SystemAccount) session.getAttribute("user");

        return mapper.toResponse(articleService.createNewsArticle(dto, user));
    }

    @PutMapping("/{id}")
    public NewsArticleResponse update(@PathVariable Long id,
                              @RequestBody NewsArticleRequest dto,
                              HttpSession session) {

        SystemAccount user =
                (SystemAccount) session.getAttribute("user");

        return mapper.toResponse(articleService.updateNewsArticle(id, dto, user));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        articleService.deleteNewsArticle(id);
    }
}
