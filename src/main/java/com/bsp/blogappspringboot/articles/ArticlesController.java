package com.bsp.blogappspringboot.articles;

import com.bsp.blogappspringboot.users.UserEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticlesController {
    private ArticlesService articlesService;

    public ArticlesController(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    @GetMapping("")
    public Iterable<ArticleEntity> getArticles() {
        return articlesService.getAllArticles();
    }

    @GetMapping("{id}")
    public String getArticleById(@PathVariable("id") String id) {
        return "get article with id: " + id;
    }

    @PostMapping("")
    public String createArticle(@AuthenticationPrincipal UserEntity user) {
        return "create article called by " + user.getUsername();
    }
}
