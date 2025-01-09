package pe.com.bibliAI.BibliAI.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import pe.com.bibliAI.BibliAI.entity.Article;
import pe.com.bibliAI.BibliAI.entity.ArticleDTO;
import pe.com.bibliAI.BibliAI.service.ArticleService;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "http://localhost:4200")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseEntity<Article> saveArticle(@Valid @RequestBody Article article) {
        try {
            Article savedArticle = articleService.saveArticle(article);
            return new ResponseEntity<>(savedArticle, HttpStatus.CREATED);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        try {
            ArticleDTO article = articleService.getArticleById(id);
            return new ResponseEntity<>(article, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        try {
            List<ArticleDTO> articles = articleService.getAllArticles();
            return new ResponseEntity<>(articles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticleById(@PathVariable Long id) {
        try {
            articleService.deleteArticleById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @Valid @RequestBody Article updatedArticle) {
        try {
            Article article = articleService.updateArticle(id, updatedArticle);
            return new ResponseEntity<>(article, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarArticuloPorNombre(@RequestParam String nombre) {
    	ArticleDTO articulo = articleService.obtenerArticuloPorNombre(nombre);
        if (articulo != null) {
            return ResponseEntity.ok(articulo);
        } else {
            return ResponseEntity.status(404).body("Artículo no encontrado");
        }
    }
    @GetMapping("/por-categoria")
    public ResponseEntity<List<ArticleDTO>> obtenerArticulosPorCategoria(
            @RequestParam String categoria, 
            @RequestParam String tituloActual) {
        
        List<ArticleDTO> articulos = articleService.obtenerArticulosPorNombreCategoria(categoria);
       
        if (articulos.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        }

        articulos = articulos.stream()
                .filter(item -> !item.getTitle().equals(tituloActual))
                .collect(Collectors.toList());

        Collections.shuffle(articulos); 

        List<ArticleDTO> recomendacionesAleatorias = articulos.stream()
                .limit(6)
                .collect(Collectors.toList());

        return ResponseEntity.ok(recomendacionesAleatorias);
    }

}
