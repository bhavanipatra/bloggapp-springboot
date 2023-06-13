package com.bsp.blogappspringboot.comments;

import com.bsp.blogappspringboot.articles.ArticlesService;
import com.bsp.blogappspringboot.comments.dtos.CommentResponse;
import com.bsp.blogappspringboot.comments.dtos.CreateCommentRequest;
import com.bsp.blogappspringboot.common.dtos.ErrorResponse;
import com.bsp.blogappspringboot.users.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/articles/{articleId}/comments")
public class CommentsController {

    public CommentsService commentsService;
    private ModelMapper modelMapper;

    public CommentsController(CommentsService commentsService, ModelMapper modelMapper) {
        this.commentsService = commentsService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("")
    public ResponseEntity<CommentResponse> addComment(@PathVariable("articleId") Long articleId, @RequestBody CreateCommentRequest c) {
        var newComment = commentsService.addComment(articleId, c);
        CommentResponse commentResponse = modelMapper.map(newComment, CommentResponse.class);
        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping("")
    public ResponseEntity<List<CommentResponse>> getCommentsOfArticle(@PathVariable("articleId") Long articleId) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        var comments = commentsService.getCommentsOfArticle(articleId);
        for(CommentEntity comment: comments) {
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setId(comment.getId());
            commentResponse.setTitle(comment.getTitle());
            commentResponse.setBody(comment.getBody());
            commentResponse.setArticleTitle(comment.getArticle().getTitle());
            commentResponse.setAuthorUserName(comment.getAuthor().getUsername());
            commentResponses.add(commentResponse);
        }
        return ResponseEntity.ok(commentResponses);
    }

    @ExceptionHandler({
            CommentsService.CommentsNotFoundException.class,
            ArticlesService.ArticleNotFoundException.class,
            UsersService.UserNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleCommentExceptions(Exception ex) {
        String message;
        HttpStatus status;

        if(ex instanceof CommentsService.CommentsNotFoundException || ex instanceof ArticlesService.ArticleNotFoundException || ex instanceof UsersService.UserNotFoundException) {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        } else {
            message = "Something went wrong!";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        ErrorResponse response = ErrorResponse.builder()
                .message(message)
                .build();
        return ResponseEntity.status(status).body(response);
    }
}
