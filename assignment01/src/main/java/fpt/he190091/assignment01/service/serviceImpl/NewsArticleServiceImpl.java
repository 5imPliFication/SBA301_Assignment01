package fpt.he190091.assignment01.service.serviceImpl;

import fpt.he190091.assignment01.dtos.NewsArticleRequest;
import fpt.he190091.assignment01.entity.Category;
import fpt.he190091.assignment01.entity.NewsArticle;
import fpt.he190091.assignment01.entity.SystemAccount;
import fpt.he190091.assignment01.entity.Tag;
import fpt.he190091.assignment01.repository.CategoryRepository;
import fpt.he190091.assignment01.repository.NewsArticleRepository;
import fpt.he190091.assignment01.repository.TagRepository;
import fpt.he190091.assignment01.service.NewsArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class NewsArticleServiceImpl implements NewsArticleService {
    private final NewsArticleRepository articleRepo;
    private final CategoryRepository categoryRepo;
    private final TagRepository tagRepo;

    @Override
    public List<NewsArticle> findAllNewsArticles() {
        return articleRepo.findAllWithRelations(); // Use JOIN FETCH
    }

    @Override
    public NewsArticle findById(Long id) {
        return articleRepo.findByIdWithRelations(id) // Use JOIN FETCH
                .orElseThrow(() -> new RuntimeException("Article not found"));
    }

    @Override
    public NewsArticle createNewsArticle(NewsArticleRequest dto, SystemAccount creator) {
        NewsArticle news = new NewsArticle();

        news.setNewsTitle(dto.getNewsTitle());
        news.setHeadline(dto.getHeadline());
        news.setNewsContent(dto.getNewsContent());
        news.setNewsSource(dto.getNewsSource());
        news.setNewsStatus(dto.getNewsStatus());
        news.setCreatedDate(LocalDateTime.now());
        news.setCreatedBy(creator);

        if (dto.getCategoryId() != null) {
            news.setCategory(categoryRepo.findById(dto.getCategoryId()).orElse(null));
        }

        // Set tags
        if (dto.getTagIds() != null && !dto.getTagIds().isEmpty()) {
            Set<Tag> tags = new HashSet<>();
            for (Long tagId : dto.getTagIds()) {
                Tag tag = tagRepo.findById(tagId)
                        .orElseThrow(() -> new RuntimeException("Tag not found: " + tagId));
                tags.add(tag);
            }
            news.setTags(tags);
        }

        return articleRepo.save(news);
    }

    @Transactional
    @Override
    public NewsArticle updateNewsArticle(Long id, NewsArticleRequest request, SystemAccount author) {
        NewsArticle article = findById(id); // This uses JOIN FETCH

        article.setNewsTitle(request.getNewsTitle());
        article.setHeadline(request.getHeadline());
        article.setNewsContent(request.getNewsContent());
        article.setNewsSource(request.getNewsSource());
        article.setNewsStatus(request.getNewsStatus());

        // Update category
        if (request.getCategoryId() != null) {
            Category category = categoryRepo.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            article.setCategory(category);
        }

        // IMPORTANT: Clear and update tags properly
        if (article.getTags() == null) {
            article.setTags(new HashSet<>());
        }

        article.getTags().clear(); // Clear existing tags

        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            for (Long tagId : request.getTagIds()) {
                Tag tag = tagRepo.findById(tagId)
                        .orElseThrow(() -> new RuntimeException("Tag not found: " + tagId));
                article.getTags().add(tag);
            }
        }

        return articleRepo.save(article);
    }

    @Override
    public void deleteNewsArticle(Long id) {
        articleRepo.deleteById(id);
    }
}
