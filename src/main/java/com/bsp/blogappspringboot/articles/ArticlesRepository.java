package com.bsp.blogappspringboot.articles;

import com.bsp.blogappspringboot.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticlesRepository extends JpaRepository<ArticleEntity, Long> {

    ArticleEntity findBySlug(String slug);

    Iterable<ArticleEntity> findByAuthor(Optional<UserEntity> author);
}
