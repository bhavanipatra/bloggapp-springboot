package com.bsp.blogappspringboot.comments;

import com.bsp.blogappspringboot.articles.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByArticle(ArticleEntity article);
}
