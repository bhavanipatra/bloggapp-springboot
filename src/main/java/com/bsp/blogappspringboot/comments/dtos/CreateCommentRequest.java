package com.bsp.blogappspringboot.comments.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Data
@Setter(AccessLevel.NONE)
public class CreateCommentRequest {
    @Nullable
    private String title;
    @NonNull
    private String body;
    @NonNull
    private Long userId;
}
