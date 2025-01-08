package pe.com.bibliAI.BibliAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.bibliAI.BibliAI.entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
