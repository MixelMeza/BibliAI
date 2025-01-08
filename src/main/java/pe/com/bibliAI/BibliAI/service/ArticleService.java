package pe.com.bibliAI.BibliAI.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bibliAI.BibliAI.entity.Article;
import pe.com.bibliAI.BibliAI.repository.ArticleRepository;

@Service
public class ArticleService {
	@Autowired
	private ArticleRepository articleRepository;

	public Article saveArticle(Article article) {
		article.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		article.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		return articleRepository.save(article);
	}

	public Article getArticleById(Long id) {
		return articleRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Artículo no encontrado con ID: " + id));
	}

	public List<Article> getAllArticles() {
		return articleRepository.findAll();
	}

	public void deleteArticleById(Long id) {
		if (articleRepository.existsById(id)) {
			articleRepository.deleteById(id);
		} else {
			throw new RuntimeException("No se puede eliminar. Artículo no encontrado con ID: " + id);
		}
	}

	public Article updateArticle(Long id, Article updatedArticle) {

	    // Buscar el artículo en la base de datos
	    Article article = articleRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Artículo no encontrado con ID: " + id));

	    // Actualizar los campos del artículo, excepto createdAt
	    article.setTitle(updatedArticle.getTitle());
	    article.setContent(updatedArticle.getContent());
	    article.setMediaUrl(updatedArticle.getMediaUrl());
	    article.setCategory(updatedArticle.getCategory());
	    article.setUser(updatedArticle.getUser());
	    
	    // Actualizar el campo updatedAt
	    article.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

	    // Guardar el artículo actualizado
	    return articleRepository.save(article);
	}

}
