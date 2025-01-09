package pe.com.bibliAI.BibliAI.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bibliAI.BibliAI.entity.Article;
import pe.com.bibliAI.BibliAI.entity.ArticleDTO;
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

	  public ArticleDTO getArticleById(Long id) {
	        Optional<Article> article = articleRepository.findById(id);
	        return article.map(this::convertToDTO).orElseThrow(() -> new RuntimeException("Article not found"));
	    }


	    public List<ArticleDTO> getAllArticles() {
	        List<Article> articles = articleRepository.findAll();
	        return articles.stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    }
	public void deleteArticleById(Long id) {
		if (articleRepository.existsById(id)) {
			articleRepository.deleteById(id);
		} else {
			throw new RuntimeException("No se puede eliminar. Artículo no encontrado con ID: " + id);
		}
	}

	public Article updateArticle(Long id, Article updatedArticle) {


	    Article article = articleRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Artículo no encontrado con ID: " + id));


	    article.setTitle(updatedArticle.getTitle());
	    article.setContent(updatedArticle.getContent());
	    article.setMediaUrl(updatedArticle.getMediaUrl());
	    article.setCategory(updatedArticle.getCategory());
	    article.setAutores(updatedArticle.getAutores());
	    article.setReferencias(updatedArticle.getReferencias());
	    
	    article.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

	    return articleRepository.save(article);
	}
	public ArticleDTO obtenerArticuloPorNombre(String nombre) {
	    Article article = articleRepository.findByTitle(nombre);
	    if (article != null) {
	        return convertToDTO(article);	    }
	    throw new RuntimeException("Article not found with title: " + nombre);
	}


	public List<ArticleDTO> obtenerArticulosPorNombreCategoria(String nombreCategoria) {
	    List<Article> articles = articleRepository.findByCategoriaNombre(nombreCategoria);
	    if (!articles.isEmpty()) {
	        return articles.stream()
	                       .map(this::convertToDTO) 
	                       .collect(Collectors.toList());
	    }
	    throw new RuntimeException("No articles found for category: " + nombreCategoria);
	}
	 
	 private ArticleDTO convertToDTO(Article article) {
	        return ArticleDTO.builder()
	                .id(article.getId())
	                .category(article.getCategory())
	                .title(article.getTitle())
	                .content(article.getContent())
	                .mediaUrl(article.getMediaUrl())
	                .createdAt(article.getCreatedAt())
	                .updatedAt(article.getUpdatedAt())
	                .autores(article.getAutores())
	                .referencias(article.getReferencias())
	                .build();
	    }
}
