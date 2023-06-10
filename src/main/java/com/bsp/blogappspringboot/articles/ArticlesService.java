package com.bsp.blogappspringboot.articles;

import com.bsp.blogappspringboot.articles.dtos.CreateArticleRequest;
import com.bsp.blogappspringboot.articles.dtos.UpdateArticleRequest;
import com.bsp.blogappspringboot.users.UsersRepository;
import com.bsp.blogappspringboot.users.UsersService;
import org.springframework.stereotype.Service;

@Service
public class ArticlesService {

    private ArticlesRepository articlesRepository;
    private UsersRepository usersRepository;

    public ArticlesService (ArticlesRepository articlesRepository, UsersRepository usersRepository) {
        this.articlesRepository = articlesRepository;
        this.usersRepository = usersRepository;
    }

    public Iterable<ArticleEntity> getAllArticles() {
        return articlesRepository.findAll();
    }

    public ArticleEntity getArticleBySlug(String slug) {
        var article = articlesRepository.findBySlug(slug);
        if (article == null) {
            throw new ArticleNotFoundException(slug);
        }
        return article;
    }
    public ArticleEntity createArticle(CreateArticleRequest a, Long authorId) {
        var author = usersRepository.findById(authorId).orElseThrow(() -> new UsersService.UserNotFoundException(authorId));
        var article = ArticleEntity.builder()
                .title(a.getTitle())
                .slug(a.getTitle().toLowerCase().replaceAll("\\s+", "-"))
                .body(a.getBody())
                .subtitle(a.getSubtitle())
                .author(author)
                .build();

        return articlesRepository.save(article);
    }

    public ArticleEntity updateArticle(Long articleId, UpdateArticleRequest u) {
        var article = articlesRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
        if(u.getTitle() != null) {
            article.setTitle(u.getTitle());
        }
        if(u.getBody() != null) {
            article.setBody(u.getBody());
        }
        if(u.getSubtitle() != null) {
            article.setSubtitle(u.getSubtitle());
        }
        return articlesRepository.save(article);
    }

    public void deleteArticle(Long articleId) {
        articlesRepository.deleteById(articleId);
    }
    static class ArticleNotFoundException extends IllegalArgumentException {
        public ArticleNotFoundException (String slug) {
            super("Article: " + slug + " not found");
        }
        public ArticleNotFoundException (Long id) {
            super("Article with id: " + id + " not found");
        }

    }


}
