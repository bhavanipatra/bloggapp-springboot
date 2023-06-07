package com.bsp.blogappspringboot.comments;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles/{article-slug}/comments")
public class CommentsController {

    @GetMapping("")
    public String getComments(@PathVariable("article-slug") String slug) {
        return "Slug -> " + slug +" Comments";
    }
}
