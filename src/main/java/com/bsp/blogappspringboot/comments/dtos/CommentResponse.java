package com.bsp.blogappspringboot.comments.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class CommentResponse {
    private Long id;
    private String title;
    private String body;
    private String authorUserName;
    private String articleTitle;
}
