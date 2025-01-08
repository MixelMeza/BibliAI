package pe.com.bibliAI.BibliAI.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bibliAI.BibliAI.entity.Category;
import pe.com.bibliAI.BibliAI.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public Category saveCategory(Category category) {
		category.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		return categoryRepository.save(category);
	}

	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
	}

	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	public void deleteCategoryById(Long id) {
		if (categoryRepository.existsById(id)) {
			categoryRepository.deleteById(id);
		} else {
			throw new RuntimeException("No se puede eliminar. Categoría no encontrada con ID: " + id);
		}
	}

	public Category updateCategory(Long id, Category updatedCategory) {

		categoryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));

		return categoryRepository.save(updatedCategory);
	}
}
