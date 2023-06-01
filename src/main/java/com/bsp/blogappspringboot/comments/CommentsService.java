package com.bsp.blogappspringboot.comments;

import org.springframework.stereotype.Service;

@Service
public class CommentsService {
    private CommentsRepository commentsRepository;

    public CommentsService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }
}
