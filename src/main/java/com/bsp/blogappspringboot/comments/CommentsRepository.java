package com.bsp.blogappspringboot.comments;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<CommentEntity, Long> {

}
