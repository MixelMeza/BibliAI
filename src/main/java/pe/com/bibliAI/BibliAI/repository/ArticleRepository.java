package pe.com.bibliAI.BibliAI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.com.bibliAI.BibliAI.entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	Article findByTitle(String nombre);
	
	@Query("SELECT a FROM Article a WHERE a.category.name = :nombreCategoria")
    List<Article> findByCategoriaNombre(@Param("nombreCategoria") String nombreCategoria);
}
