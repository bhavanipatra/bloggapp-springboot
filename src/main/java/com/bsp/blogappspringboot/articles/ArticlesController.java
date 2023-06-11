package com.bsp.blogappspringboot.articles;

import com.bsp.blogappspringboot.articles.dtos.CreateArticleRequest;
import com.bsp.blogappspringboot.articles.dtos.UpdateArticleRequest;
import com.bsp.blogappspringboot.common.dtos.ErrorResponse;
import com.bsp.blogappspringboot.users.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/articles")
public class ArticlesController {
    private ArticlesService articlesService;

    public ArticlesController(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<ArticleEntity>> getArticles() {
        return ResponseEntity.ok(articlesService.getAllArticles());
    }

    @GetMapping("{slug}")
    public ResponseEntity<ArticleEntity> getArticleBySlug(@PathVariable("slug") String slug) {
        return ResponseEntity.ok(articlesService.getArticleBySlug(slug));
    }

    @PostMapping("{authorId}")
    public ResponseEntity<ArticleEntity> createArticle(@RequestBody CreateArticleRequest a, @PathVariable("authorId") Long authorId) {
        var createdArticle = articlesService.createArticle(a, authorId);
        URI savedArticleUri = URI.create("/articles/" + createdArticle.getId());
        return ResponseEntity.created(savedArticleUri).body(createdArticle);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ArticleEntity> updateArticle(@RequestBody UpdateArticleRequest a, @PathVariable("id") Long articleId) {
        var updatedArticle = articlesService.updateArticle(articleId, a);
        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable("id") Long articleId) {
        articlesService.deleteArticle(articleId);
        return ResponseEntity.ok("The article with article id: " + articleId + " has been deleted");
    }

    @GetMapping("/user/{authorId}")
    public ResponseEntity<Iterable<ArticleEntity>> getArticlesOfAuthor(@PathVariable("authorId") Long authorId) {
        var articles = articlesService.getArticlesOfAuthor(authorId);
        return ResponseEntity.ok(articles);
    }

    @ExceptionHandler({
            ArticlesService.ArticleNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleArticleExceptions(Exception ex) {
        String message;
        HttpStatus status;

        if(ex instanceof ArticlesService.ArticleNotFoundException) {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        } else {
            message = "Something went wrong";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        ErrorResponse response = ErrorResponse.builder()
                .message(message)
                .build();
        return ResponseEntity.status(status).body(response);
    }
}
