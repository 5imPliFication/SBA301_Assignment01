package fpt.he190091.assignment01.service.serviceImpl;

import fpt.he190091.assignment01.dtos.NewsArticleRequest;
import fpt.he190091.assignment01.entity.NewsArticle;
import fpt.he190091.assignment01.entity.SystemAccount;
import fpt.he190091.assignment01.repository.CategoryRepository;
import fpt.he190091.assignment01.repository.NewsArticleRepository;
import fpt.he190091.assignment01.repository.TagRepository;
import fpt.he190091.assignment01.service.NewsArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsArticleServiceImpl implements NewsArticleService {
    private final NewsArticleRepository articleRepo;
    private final CategoryRepository categoryRepo;
    private final TagRepository tagRepo;

    @Override
    public List<NewsArticle> findAllNewsArticles() {
        return articleRepo.findAll();
    }

    @Override
    public NewsArticle findById(Long id) {
        return articleRepo.findById(id).orElse(null);
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

        if (dto.getTagIds() != null) {
            news.setTags(new HashSet<>(tagRepo.findAllById(dto.getTagIds())));
        }

        return articleRepo.save(news);
    }

    @Override
    public NewsArticle updateNewsArticle(Long id, NewsArticleRequest dto, SystemAccount updater) {
        NewsArticle news = findById(id);
        if (news == null) return null;

        if (dto.getNewsTitle() != null)
            news.setNewsTitle(dto.getNewsTitle());

        if (dto.getHeadline() != null)
            news.setHeadline(dto.getHeadline());

        if (dto.getNewsContent() != null)
            news.setNewsContent(dto.getNewsContent());

        if (dto.getNewsSource() != null)
            news.setNewsSource(dto.getNewsSource());

        if (dto.getNewsStatus() != null)
            news.setNewsStatus(dto.getNewsStatus());

        if (dto.getCategoryId() != null)
            news.setCategory(categoryRepo.findById(dto.getCategoryId()).orElse(null));

        if (dto.getTagIds() != null)
            news.setTags(new HashSet<>(tagRepo.findAllById(dto.getTagIds())));

        news.setUpdatedBy(updater);
        news.setModifiedDate(LocalDateTime.now());

        return articleRepo.save(news);
    }

    @Override
    public void deleteNewsArticle(Long id) {
        articleRepo.deleteById(id);
    }
}
