package com.bsp.blogappspringboot.articles;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticlesRepository extends JpaRepository<ArticleEntity, Long> {

}
