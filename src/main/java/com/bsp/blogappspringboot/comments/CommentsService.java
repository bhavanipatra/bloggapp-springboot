package com.bsp.blogappspringboot.comments;

import com.bsp.blogappspringboot.articles.ArticlesRepository;
import com.bsp.blogappspringboot.articles.ArticlesService;
import com.bsp.blogappspringboot.comments.dtos.CreateCommentRequest;
import com.bsp.blogappspringboot.users.UsersRepository;
import com.bsp.blogappspringboot.users.UsersService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {
    private CommentsRepository commentsRepository;
    private UsersRepository usersRepository;
    private ArticlesRepository articlesRepository;

    public CommentsService(CommentsRepository commentsRepository, UsersRepository usersRepository, ArticlesRepository articlesRepository) {
        this.commentsRepository = commentsRepository;
        this.usersRepository = usersRepository;
        this.articlesRepository = articlesRepository;
    }

    public CommentEntity addComment(Long articleId, CreateCommentRequest c) {
        var article = articlesRepository.findById(articleId).orElseThrow(() -> new ArticlesService.ArticleNotFoundException(articleId));
        var user = usersRepository.findById(c.getUserId()).orElseThrow(() -> new UsersService.UserNotFoundException(c.getUserId()));
        var newComment = CommentEntity.builder()
                .title(c.getTitle())
                .body(c.getBody())
                .article(article)
                .author(user)
                .build();
        return commentsRepository.save(newComment);
    }

    public List<CommentEntity> getCommentsOfArticle(Long articleId) {
        var article = articlesRepository.findById(articleId).orElseThrow(() -> new ArticlesService.ArticleNotFoundException(articleId));
        var comments = commentsRepository.findByArticle(article);
        if(comments.isEmpty()) {
            throw new CommentsNotFoundException(articleId);
        }
        return comments;
    }

    public static class CommentsNotFoundException extends IllegalArgumentException {
        public CommentsNotFoundException(Long articleId) {
            super("For article id: " + articleId + ", NO COMMENTS WERE FOUND!");
        }
    }
}
